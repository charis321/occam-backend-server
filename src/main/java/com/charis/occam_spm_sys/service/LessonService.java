package com.charis.occam_spm_sys.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.charis.occam_spm_sys.entity.Lesson;

public interface LessonService extends IService<Lesson>{
	public List<Lesson> getLessonListByCourseId(Long courseId);
	public List<Lesson> getLessonListDefault(Long courseId);
//	public Boolean createLesson(Lesson lesson);
//	public Boolean createLesson(List<Lesson> lesson);
	public Boolean deleteLesson(Integer lessonId);
	public Boolean eraseLesson(Long courseId);
	
	public Boolean startRegistering(Lesson lesson);
}
