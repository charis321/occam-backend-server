package com.charis.occam_spm_sys.service.impl;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.charis.occam_spm_sys.entity.Lesson;
import com.charis.occam_spm_sys.entity.Rollcall;
import com.charis.occam_spm_sys.exception.BusinessException;
import com.charis.occam_spm_sys.mapper.LessonMapper;
import com.charis.occam_spm_sys.mapper.RollcallMapper;
import com.charis.occam_spm_sys.model.dto.RollcallDTO;
import com.charis.occam_spm_sys.model.vo.RollcallVO;
import com.charis.occam_spm_sys.service.RollcallService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RollcallServiceImpl extends ServiceImpl<RollcallMapper, Rollcall> implements RollcallService {
	
	private final Map<Integer, ScheduledFuture<?>> rotationTasks = new ConcurrentHashMap<>();
	private static final Random RANDOM = new Random();

	@Autowired
	private LessonMapper lessonMapper;

	@Autowired
	private ThreadPoolTaskScheduler taskScheduler;
	
	@Autowired
    private SimpMessagingTemplate messagingTemplate;

	@Override
	public RollcallVO getRollcallByLessonId(Integer lessonId) {
		Rollcall rollcall = this.getById(lessonId);
		if(rollcall==null) return null;
		RollcallVO vo = new RollcallVO();
		BeanUtils.copyProperties(rollcall, vo);
		return vo;
	}

	
	@Override
	@Transactional
	public void handleRollcall(RollcallDTO rollcallDTO) {
		Lesson lesson = lessonMapper.selectById(rollcallDTO.getLessonId());
		if (lesson == null) {
			log.warn("找不到該堂課紀錄 | lessonId: ",rollcallDTO.getLessonId());
			throw new BusinessException(404, "找不到該堂課紀錄");
		}
		
		if(rollcallDTO.getMode()==0) {
			if(rollcallDTO.getStatus()==1){
				if(rollcallDTO.getRotationTime()>0) startRotation(rollcallDTO);
			}else {
				stopRotation(rollcallDTO.getLessonId());
			}			
			saveOrUpdateRollcall(rollcallDTO);
		
		}else if(rollcallDTO.getMode()==1){
			//ws-rollcall
			
		}else {
			log.warn("未知點名模式 | Rollcall: ", rollcallDTO);
			throw new BusinessException(400, "未知點名模式");
		}
	}
	
	@Override
	public void saveOrUpdateRollcall(RollcallDTO rollcallDTO) {
		Rollcall rollcall = new Rollcall();
		BeanUtils.copyProperties(rollcallDTO, rollcall);
		Boolean res = this.saveOrUpdate(rollcall);
		
		if(!res) {
			log.error("伺服器發生錯誤");
			throw new BusinessException(500, "伺服器發生錯誤");
		}
	}
	
	private void startRotation(RollcallDTO rollcallDTO) {
		stopRotation(rollcallDTO.getLessonId());		
		var task =  taskScheduler.scheduleAtFixedRate(() -> {
			String code = generateRollcallCode();
			String destination = "/topic/rollcall/"+ rollcallDTO.getLessonId()+"/code";
			
			OffsetDateTime now = OffsetDateTime.now();
			OffsetDateTime nextRotationTime =  now.plusSeconds(rollcallDTO.getRotationTime());
			
			Map<String, Object> claims = new HashMap<>();
			claims.put("code", code);
			claims.put("nextRotationTime", nextRotationTime);
			
			
			Boolean res = this.lambdaUpdate().eq(Rollcall::getLessonId, rollcallDTO.getLessonId())
					.set(Rollcall::getCode, code)
					.set(Rollcall::getNextRotationTime, nextRotationTime)
					.update();
			
			messagingTemplate.convertAndSend(destination, claims);
			log.info("點名碼輪換 | id: {}，code: {}，rotation: {}，destination: {}", rollcallDTO.getLessonId(), code, rollcallDTO.getRotationTime(),destination);
		}, Duration.ofSeconds(rollcallDTO.getRotationTime()));
		
		rotationTasks.put(rollcallDTO.getLessonId(), task);  
	}
	
	private void stopRotation(Integer lessonId) {
		ScheduledFuture<?> task = rotationTasks.remove(lessonId);
		if (task!=null) {
			task.cancel(true);
			log.debug("已停止課程的輪換點名碼任務 | lessonId: {}", lessonId);
		}
	}
	
	private String generateRollcallCode() {
		return String.format("%06d", RANDOM.nextInt(1000000));
	}

}
