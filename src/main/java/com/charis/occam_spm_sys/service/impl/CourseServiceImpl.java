package com.charis.occam_spm_sys.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.charis.occam_spm_sys.entity.Attendance;
import com.charis.occam_spm_sys.entity.Course;
import com.charis.occam_spm_sys.entity.User;
import com.charis.occam_spm_sys.mapper.CourseMapper;
import com.charis.occam_spm_sys.service.CourseService;
import com.charis.occam_spm_sys.service.EnrollmentService;
import com.charis.occam_spm_sys.service.LessonService;
import com.charis.occam_spm_sys.service.UserService;
import com.charis.occam_spm_sys.vo.CourseDetailVO;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
	
	@Autowired
	private UserService userService;
	@Autowired
	private EnrollmentService enrollmentService;
	@Autowired
	private LessonService lessonService;
	
	@Override
	public List<Course> findCourseByTeacherId(Long teacherId){
		LambdaQueryWrapper<Course> lambdaQueryWrapper = new LambdaQueryWrapper<Course>();
		lambdaQueryWrapper.eq(Course::getTeacherId, teacherId);
		List<Course> res = this.list(lambdaQueryWrapper);
		return res;
	}

	@Override
	public List<Course> findCourseById(Long courseId) {
//		LambdaQueryWrapper<Course> lambdaQueryWrapper = new LambdaQueryWrapper<Course>();
//		lambdaQueryWrapper.eq(Course::getTeacherId, Id);
//		List<Course> res = this.list(lambdaQueryWrapper);
		return null;
	}

	@Override
	public CourseDetailVO getCourseDetailVO(Long courseId) {
		CourseDetailVO vo = new CourseDetailVO();
		Course course = this.getById(courseId);
		System.out.println("course:");
		System.out.println(courseId);
		BeanUtils.copyProperties(course, vo);
		vo.setTeacherName(userService.getById(course.getTeacherId()).getName());
		vo.setStudentCount(enrollmentService.getStudentCountByCourseId(courseId));
		return vo;
	}

	@Override
	public List<CourseDetailVO> getCourseDetailVOByTeacherId(Long teacherId) {
		User teacher= userService.getById(teacherId);
		if(teacher==null) return null;
		
		LambdaQueryWrapper<Course> lambdaQueryWrapper = new LambdaQueryWrapper<Course>();
		lambdaQueryWrapper.eq(Course::getTeacherId, teacherId);
		
		List<CourseDetailVO> voList =  this.list(lambdaQueryWrapper)
										   .stream()
										   .map(course -> {
											   CourseDetailVO vo = new CourseDetailVO();
											   BeanUtils.copyProperties(course, vo);
											   vo.setTeacherName(teacher.getName());
											   vo.setStudentCount(enrollmentService.getStudentCountByCourseId(course.getId()));
											   return vo;
										   })
										   .collect(Collectors.toList());
		
		return voList;
	}

	@Override
	@Transactional
	public Boolean eraseCourse(Long courseId) {
		lessonService.eraseLesson(courseId);
		enrollmentService.eraseEnrollment(courseId);
		this.removeById(courseId);
		return true;
	}
	
	
}
