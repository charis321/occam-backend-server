package com.charis.occam_spm_sys.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.charis.occam_spm_sys.entity.Course;
import com.charis.occam_spm_sys.entity.Enrollment;
import com.charis.occam_spm_sys.entity.Student;
import com.charis.occam_spm_sys.entity.User;
import com.charis.occam_spm_sys.mapper.EnrollmentMapper;
import com.charis.occam_spm_sys.service.EnrollmentService;
import com.charis.occam_spm_sys.service.StudentService;
import com.charis.occam_spm_sys.service.UserService;

@Service
public class EnrollmentServiceImpl extends ServiceImpl<EnrollmentMapper, Enrollment> implements EnrollmentService{
	
	@Autowired
	private StudentService studentService;
	
	@Override
	public List<Course> getCourseListByStudentId(Long studentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Student> getStudentListByCourseId(Long courseId) {
		LambdaQueryWrapper<Enrollment> lamdbaQueryWrapper = new LambdaQueryWrapper<Enrollment>();
        lamdbaQueryWrapper.eq(Enrollment::getCourseId, courseId);
        List<Student> res = this.list(lamdbaQueryWrapper)
        						.stream()
        						.map(enrollment->{
        							return studentService.getById(enrollment.getStudentId());
        						})
        						.collect(Collectors.toList());
		return res;
	}

	@Override
	public List<Enrollment> getByCompoundId(Long courseId, Long studentId) {
		LambdaQueryWrapper<Enrollment> lamdbaQueryWrapper = new LambdaQueryWrapper<Enrollment>();
        lamdbaQueryWrapper.eq(Enrollment::getCourseId, courseId)
         				  .eq(Enrollment::getStudentId, studentId);
        List<Enrollment> res = this.list(lamdbaQueryWrapper);
		return res;
	}

	@Override
	public Boolean enrollStudentListByCourseId(List<Student> studentList, Long courseId) {
		List<Enrollment> enrollmentList = studentList.stream()
													.map(student->{
														Enrollment e = new Enrollment();
														e.setCourseId(courseId);
														e.setStudentId(student.getId());
														return e;
													})
													.collect(Collectors.toList());		
		return this.saveBatch(enrollmentList);
	}

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
	
}
