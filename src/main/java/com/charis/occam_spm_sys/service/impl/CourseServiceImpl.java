package com.charis.occam_spm_sys.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.charis.occam_spm_sys.entity.Course;
import com.charis.occam_spm_sys.entity.User;
import com.charis.occam_spm_sys.mapper.CourseMapper;
import com.charis.occam_spm_sys.model.dto.CourseQueryDTO;
import com.charis.occam_spm_sys.model.dto.CourseUpdateDTO;
import com.charis.occam_spm_sys.model.vo.CourseDetailVO;
import com.charis.occam_spm_sys.model.vo.UserInfoVO;
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
		return this.lambdaQuery().eq(Course::getTeacherId, teacherId).list();
	}

	@Override
	public CourseDetailVO getCourseDetailVO(Long courseId) {
		return baseMapper.selectDetailById(courseId);
	}

	@Override
	public List<CourseDetailVO> getCourseDetailVOByTeacherId(Long teacherId, CourseQueryDTO query) {
		List<CourseDetailVO> courseVOs = baseMapper.selectDetailsByTeacherId(teacherId);
		return filterCoursesByQuery(courseVOs, query);
	}

	@Override
	public List<CourseDetailVO> getCourseDetailVOByStudentId(Long studentId, CourseQueryDTO query) {
		List<CourseDetailVO> courseVOs = baseMapper.selectDetailsByStudentId(studentId);
		return filterCoursesByQuery(courseVOs, query);
	}

	public List<CourseDetailVO> filterCoursesByQuery(List<CourseDetailVO> courseVOs, CourseQueryDTO query) {
		if (query == null) {
			return courseVOs;
		}
		return courseVOs.stream().filter(vo -> {
			boolean matchName = !StringUtils.hasText(query.getName())
					|| (vo.getName() != null && vo.getName().contains(query.getName()));

			boolean matchClassroom = !StringUtils.hasText(query.getClassroom())
					|| (vo.getClassroom() != null && vo.getClassroom().contains(query.getClassroom()));

			boolean matchSchool = !StringUtils.hasText(query.getSchool())
					|| (vo.getSchool() != null && vo.getSchool().contains(query.getSchool()));
			boolean matchDepartment = !StringUtils.hasText(query.getDepartment())
					|| (vo.getDepartment() != null && vo.getDepartment().contains(query.getDepartment()));

			boolean matchStatus = query.getStatus() == null || query.getStatus().equals(vo.getStatus());

			return matchName && matchClassroom && matchSchool && matchStatus&&matchDepartment;
		}).sorted((a, b) -> b.getId().compareTo(a.getId())).collect(Collectors.toList());
	}


//	@Override
//	public List<CourseDetailVO> getCoursesByQuery(CourseQueryDTO query) {
//		var queryWrapper = new LambdaQueryWrapper<Course>();
//		queryWrapper.eq(query.getId() != null, Course::getId, query.getId())
//				.like(StringUtils.hasText(query.getName()), Course::getName, query.getName())
//				.like(StringUtils.hasText(query.getClassroom()), Course::getClassroom, query.getClassroom())
//				.like(StringUtils.hasText(query.getSchool()), Course::getSchool, query.getSchool())
//				.like(StringUtils.hasText(query.getDepartment()), Course::getDepartment, query.getDepartment())
//				.eq(query.getStatus() != null, Course::getStatus, query.getStatus())
//				.orderByDesc(Course::getId);
//		List<Course> courses = this.list(queryWrapper);
//		List<CourseDetailVO> courseVOs = courses.stream().map(c -> {
//			CourseDetailVO vo = new CourseDetailVO();
//			BeanUtils.copyProperties(c, vo);
//			return vo;
//		}).collect(Collectors.toList());
//		
//		return  courseVOs;
//	}

	@Override
	public Boolean updateCourse(Long courseId, CourseUpdateDTO dto) {
		Course course = new Course();
		BeanUtils.copyProperties(dto, course);
		course.setId(courseId);
		return this.updateById(course);
	}

	@Override
	@Transactional
	public Boolean eraseCourse(Long courseId) {
		lessonService.deleteLessonsByCourseId(courseId);
		enrollmentService.eraseEnrollment(courseId);
		return this.removeById(courseId);
	}

}
