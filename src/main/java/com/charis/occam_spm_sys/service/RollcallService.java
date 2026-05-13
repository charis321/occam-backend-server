package com.charis.occam_spm_sys.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.charis.occam_spm_sys.entity.Rollcall;
import com.charis.occam_spm_sys.model.dto.RollcallDTO;
import com.charis.occam_spm_sys.model.dto.RollcallQueryDTO;
import com.charis.occam_spm_sys.model.vo.RollcallDetailVO;
import com.charis.occam_spm_sys.model.vo.RollcallVO;


public interface RollcallService extends IService<Rollcall>{
	
	public RollcallVO getRollcallByLessonId(Integer lessonId);
	public List<RollcallVO> getRollcallsByQuery(RollcallQueryDTO queryDTO);
	
	public List<RollcallDetailVO> getRollcallingsByStudentId(Long studentId);
	public List<RollcallDetailVO> getRollcallingsByTeacherId(Long teacherId);
	
	public void saveOrUpdateRollcall(RollcallDTO rollcallDTO);
	public void handleRollcall(RollcallDTO rollcallDTO);
}
