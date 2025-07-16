package com.charis.occam_spm_sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.charis.occam_spm_sys.entity.Lesson;
import com.charis.occam_spm_sys.entity.Student;
import com.charis.occam_spm_sys.mapper.LessonMapper;
import com.charis.occam_spm_sys.service.AttendanceService;
import com.charis.occam_spm_sys.service.LessonService;

@Service
public class LessonServiceImpl extends ServiceImpl<LessonMapper, Lesson> implements LessonService{
	
	@Autowired
	private AttendanceService attendanceService;

	@Override
	public List<Lesson> getLessonListByCourseId(Long courseId) {
		LambdaQueryWrapper<Lesson> lambdaQueryWrapper = new LambdaQueryWrapper<Lesson>();
		lambdaQueryWrapper.eq(Lesson::getCourseId, courseId);
		List<Lesson> res = this.list(lambdaQueryWrapper);
		return res;
	}
	
	@Override
	public List<Lesson> getLessonListDefault(Long courseId){
		return baseMapper.findByCourseIdDefault(courseId);
	}
	
	
//	@Override
//	@Transactional
//	public Boolean createLesson(Lesson lesson) {
//		Lesson lessonTmp = new Lesson();
//		BeanUtils.copyProperties(lesson, lessonTmp);
//		this.save(lessonTmp);
//		return attendanceService.buildLessonAttendaces(lessonTmp);
//	}
//	
//	@Override
//	@Transactional
//	public Boolean createLesson(List<Lesson> lessonList) {
//		List<Lesson> lessonListTmp = new ArrayList<Lesson>();
//		for(lesson)
//		BeanUtils.copyProperties(lesson, l);
//		return this.saveBatch(lessonList);
////		return attendanceService.buildLessonAttendaces(lessonList);
//	}
	
	
	
	@Override
	public Boolean eraseLesson(Long courseId) {
		LambdaQueryWrapper<Lesson> lambdaQueryWrapper = new LambdaQueryWrapper<Lesson>();
		lambdaQueryWrapper.eq(Lesson::getCourseId, courseId);
		return this.remove(lambdaQueryWrapper);
	}

	
	@Override
	@Transactional
	public Boolean deleteLesson(Integer lessonId) {
		attendanceService.eraseLessonAttendances(lessonId);
		return this.removeById(lessonId);
	}
	
	@Override
	public Boolean startRegistering(Lesson lesson) {
		if(lesson.getStatus() == 0) attendanceService.buildLessonAttendaces(lesson);
		lesson.setStatus(1);
		return this.updateById(lesson);
	}

	
}
