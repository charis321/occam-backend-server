package com.charis.occam_spm_sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.charis.occam_spm_sys.entity.Rollcall;
import com.charis.occam_spm_sys.model.dto.RollcallDTO;
import com.charis.occam_spm_sys.model.vo.RollcallVO;


public interface RollcallService extends IService<Rollcall>{
	
	public RollcallVO getRollcallByLessonId(Integer lessonId);
	public void saveOrUpdateRollcall(RollcallDTO rollcallDTO);
	public void handleRollcall(RollcallDTO rollcallDTO);
}
