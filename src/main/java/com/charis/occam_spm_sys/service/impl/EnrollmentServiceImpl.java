package com.charis.occam_spm_sys.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.charis.occam_spm_sys.entity.Course;
import com.charis.occam_spm_sys.entity.Enrollment;
import com.charis.occam_spm_sys.entity.User;
import com.charis.occam_spm_sys.mapper.EnrollmentMapper;
import com.charis.occam_spm_sys.service.EnrollmentService;
import com.charis.occam_spm_sys.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EnrollmentServiceImpl extends ServiceImpl<EnrollmentMapper, Enrollment> implements EnrollmentService {

	@Autowired
	private UserService userService;

	@Override
	public List<Course> getCourseListByStudentId(Long studentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getStudentListByCourseId(Long courseId) {
		LambdaQueryWrapper<Enrollment> lamdbaQueryWrapper = new LambdaQueryWrapper<Enrollment>();
		lamdbaQueryWrapper.eq(Enrollment::getCourseId, courseId);
		List<User> res = this.list(lamdbaQueryWrapper).stream().map(enrollment -> {
			return userService.getById(enrollment.getStudentId());
		}).collect(Collectors.toList());
		return res;
	}

	@Override
	public List<User> getStudentListByLessonId(Integer lessonId) {

		return null;
	}

	@Override
	public List<Enrollment> getByCompoundId(Long courseId, Long studentId) {
		LambdaQueryWrapper<Enrollment> lamdbaQueryWrapper = new LambdaQueryWrapper<Enrollment>();
		lamdbaQueryWrapper.eq(Enrollment::getCourseId, courseId).eq(Enrollment::getStudentId, studentId);
		List<Enrollment> res = this.list(lamdbaQueryWrapper);
		return res;
	}

//	@Transactional
//	@Override
//	public List<User> enrollStudentListByCourseId(List<User> studentList, Long courseId) {
//		List<User> invaildStudents = new ArrayList<User>();
//		List<Enrollment> enrollmentList = new ArrayList<Enrollment>();
//		for(User student : studentList) {
//			List<User> userList = userService.findUserByEmail(student.getEmail());
//			if (userList.size() == 1) {
//				Enrollment er = new Enrollment();
//				er.setCourseId(courseId);
//				er.setStudentId(userList.get(0).getId());
//				enrollmentList.add(er);
//			} else {
//				invaildStudents.add(student);
//			}
//		}
//		if(invaildStudents.size()==0) {		
//			this.saveBatch(enrollmentList);
//			return null;
//		}else {
//			return invaildStudents;
//		}
//	}

	@Override
	public long getStudentCountByCourseId(Long courseId) {
		LambdaQueryWrapper<Enrollment> lamdbaQueryWrapper = new LambdaQueryWrapper<Enrollment>();
		lamdbaQueryWrapper.eq(Enrollment::getCourseId, courseId);
		return this.count(lamdbaQueryWrapper);
	}

	@Override
	public Boolean eraseEnrollment(Long courseId) {
		LambdaQueryWrapper<Enrollment> lamdbaQueryWrapper = new LambdaQueryWrapper<Enrollment>();
		lamdbaQueryWrapper.eq(Enrollment::getCourseId, courseId);
		return this.remove(lamdbaQueryWrapper);
	}

	@Override
	public List<User> enrollStudentListByCourseId(List<User> studentList, Long courseId) {
		List<String> emails = studentList.stream().map(User::getEmail)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
		
		List<User> existingUsers = userService.list(new LambdaQueryWrapper<User>()
	            .in(User::getEmail, emails));
		
		List<User> existingEnrollments = this.getStudentListByCourseId(courseId);
		
		Map<String, User> existingUserMap = existingUsers.stream()
	            .collect(Collectors.toMap(User::getEmail, u -> u));
		
		Set<String> existingEnrollmentSet = existingEnrollments.stream()
				.map(User::getEmail)
	            .collect(Collectors.toSet());
		
		
		List<User> invalidStudents = new ArrayList<User>();
		List<Enrollment> enrollmentList = new ArrayList<Enrollment>();

		for(User student : studentList) {
			User targetUser = existingUserMap.get(student.getEmail());
			if (targetUser==null) {
				invalidStudents.add(student);
			}else if(existingEnrollmentSet.contains(student.getEmail())){
				log.info("自動過濾重複登記 - 學生: {}, 課程 ID: {}", student.getEmail(), courseId);
			}else {
				Enrollment er = new Enrollment();
				er.setCourseId(courseId);
				er.setStudentId(targetUser.getId());
				enrollmentList.add(er);
			}
		}
		
		if(invalidStudents.isEmpty()) {		
			this.saveBatch(enrollmentList);
			return Collections.emptyList();
		}
		
		return invalidStudents;
	}

}
