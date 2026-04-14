package com.charis.occam_spm_sys.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.charis.occam_spm_sys.entity.Lesson;
import com.charis.occam_spm_sys.model.dto.RollcallDTO;
import com.charis.occam_spm_sys.model.vo.LessonDetailVO;

public interface LessonService extends IService<Lesson>{
	public List<Lesson> getLessonListByCourseId(Long courseId);
	public List<LessonDetailVO>  getLessonListByTeacherId(Long teacherId);
	public List<LessonDetailVO> getLessonListByStudentId(Long studentId);
	
	public List<LessonDetailVO> getLessonDetailVOByCourseId(Long courseId);
	
	public Boolean deleteLesson(Integer lessonId);
	public Boolean deleteLessonsByCourseId(Long courseId);
	
	public Lesson handleRollcall(Integer lessonId, RollcallDTO rollcall);
//	public Boolean startRegistering(Lesson lesson);
}
