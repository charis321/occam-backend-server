package com.charis.occam_spm_sys.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.charis.occam_spm_sys.entity.Attendance;
import com.charis.occam_spm_sys.entity.Course;
import com.charis.occam_spm_sys.entity.Enrollment;
import com.charis.occam_spm_sys.entity.Lesson;
import com.charis.occam_spm_sys.entity.Student;
import com.charis.occam_spm_sys.mapper.AttendanceMapper;
import com.charis.occam_spm_sys.service.AttendanceService;
import com.charis.occam_spm_sys.service.EnrollmentService;
import com.charis.occam_spm_sys.service.LessonService;

@Service
public class AttendanceServiceImpl extends ServiceImpl<AttendanceMapper, Attendance> implements AttendanceService  {
	
	@Autowired
	private EnrollmentService enrollmentService;
	
	@Override
	public Boolean eraseLessonAttendances(Integer lessonId) {
		LambdaQueryWrapper<Attendance> lambdaQueryWrapper = new LambdaQueryWrapper<Attendance>();
		lambdaQueryWrapper.eq(Attendance::getLessonId, lessonId);
		return this.remove(lambdaQueryWrapper);
	}

	@Override
	public Boolean buildLessonAttendaces(Lesson lesson) {
		List<Attendance> attendanceList = enrollmentService.getStudentListByCourseId(lesson.getCourseId())
														   .stream()
														   .map(student->{
															   Attendance attendance = new Attendance();
															   attendance.setLessonId(lesson.getId());
															   attendance.setStudentId(student.getId());
															   return attendance;
														    })
														   .collect(Collectors.toList());
		
		return this.saveBatch(attendanceList);
	}
	
	@Override
	public Boolean buildLessonAttendaces(List<Lesson> lessonList) {
		for(Lesson lesson : lessonList) {
			this.buildLessonAttendaces(lesson);
		}
		return true;
	}

	@Override
	public List<Attendance> getAttendanceCourse(Long courseId) {
		List<Student> studentList = enrollmentService.getStudentListByCourseId(courseId);
		
		return null;
	}
	
	


}
