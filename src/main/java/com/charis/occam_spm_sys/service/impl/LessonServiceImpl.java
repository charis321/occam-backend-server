package com.charis.occam_spm_sys.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.charis.occam_spm_sys.entity.Lesson;
import com.charis.occam_spm_sys.mapper.LessonMapper;
import com.charis.occam_spm_sys.model.dto.RollcallDTO;
import com.charis.occam_spm_sys.model.vo.LessonDetailVO;
import com.charis.occam_spm_sys.service.AttendanceService;
import com.charis.occam_spm_sys.service.LessonService;

@Service
public class LessonServiceImpl extends ServiceImpl<LessonMapper, Lesson> implements LessonService {

	@Autowired
	private AttendanceService attendanceService;
	
//  @Autowired
//	private CourseService courseService;
	
//	@Autowired
//	private EnrollmentService enrollmentService;

	@Override
	public List<Lesson> getLessonListByCourseId(Long courseId) {
		LambdaQueryWrapper<Lesson> lambdaQueryWrapper = new LambdaQueryWrapper<Lesson>();
		lambdaQueryWrapper.eq(Lesson::getCourseId, courseId);
		List<Lesson> res = this.list(lambdaQueryWrapper);
		return res;
	}
	
	@Override
	public LessonDetailVO getLessonDetailById(Integer lessonId) {
		return baseMapper.selectDetailById(lessonId);
	}
	@Override
	public List<LessonDetailVO> getLessonDetailsByTeacherId(Long teacherId) {
		return baseMapper.selectDetailsByTeacherId(teacherId);
	}

	@Override
	public List<LessonDetailVO> getLessonDetailsByStudentId(Long studentId) {
		return baseMapper.selectDetailsByStudentId(studentId);
	}
	@Override
	public List<LessonDetailVO> getLessonDetailsByCourseId(Long courseId){
		return baseMapper.selectDetailsByCourseId(courseId);
	}

	@Override
	@Transactional
	public Boolean deleteLessonsByCourseId(Long courseId) {
		var queryWrapper = new LambdaQueryWrapper<Lesson>();
		queryWrapper.eq(Lesson::getCourseId, courseId);

		List<Integer> lessonIds = this.list(queryWrapper)
					.stream().map(l -> l.getId()).collect(Collectors.toList());

		attendanceService.deleteAttendancesByLessonId(lessonIds);
		return this.remove(queryWrapper);
	}

	@Override
	@Transactional
	public Boolean deleteLesson(Integer lessonId) {
		attendanceService.deleteAttendancesByLessonId(lessonId);
		return this.removeById(lessonId);
	}


	@Override
	public Lesson handleRollcall(Integer lessonId, RollcallDTO rollcall) {
		this.lambdaUpdate().eq(Lesson::getId, lessonId)
						   .set(Lesson::getAttendanceStatus, rollcall.getAttendance_status())
						   .set(Lesson::getAttendanceCode, rollcall.getAttendance_code())
						   .update();
		return this.getById(lessonId);
	}

}
