package com.charis.occam_spm_sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.charis.occam_spm_sys.entity.Course;
import com.charis.occam_spm_sys.mapper.CourseMapper;
import com.charis.occam_spm_sys.model.vo.CourseDetailVO;
import com.charis.occam_spm_sys.service.CourseService;
import com.charis.occam_spm_sys.service.EnrollmentService;
import com.charis.occam_spm_sys.service.LessonService;


@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

	@Autowired
	private EnrollmentService enrollmentService;
	@Autowired
	private LessonService lessonService;

	@Override
	public List<Course> getCoursesByTeacherId(Long teacherId) {
		return  this.lambdaQuery()
					.eq(Course::getTeacherId, teacherId)
					.list();
	}

	@Override
	public CourseDetailVO getCourseDetailVO(Long courseId) {
		return baseMapper.selectDetailById(courseId);
	}

	@Override
	public List<CourseDetailVO> getCourseDetailVOByTeacherId(Long teacherId) {
		return baseMapper.selectDetailsByTeacherId(teacherId);
	}

	@Override
	public List<CourseDetailVO> getCourseDetailVOByStudentId(Long studentId) {
		return baseMapper.selectDetailsByStudentId(studentId);
	}

	@Override
	@Transactional
	public Boolean eraseCourse(Long courseId) {
		lessonService.deleteLessonsByCourseId(courseId);
		enrollmentService.eraseEnrollment(courseId);
		return this.removeById(courseId);
	}

}
