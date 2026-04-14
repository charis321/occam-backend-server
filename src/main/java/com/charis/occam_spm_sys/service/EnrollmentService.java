package com.charis.occam_spm_sys.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.charis.occam_spm_sys.entity.Course;
import com.charis.occam_spm_sys.entity.Enrollment;
import com.charis.occam_spm_sys.entity.User;

public interface EnrollmentService extends IService<Enrollment> {
	public List<Course> getCourseListByStudentId(Long studentsId);
	public List<User> getStudentListByCourseId(Long courseId);
	public List<User> getStudentListByLessonId(Integer lessonId);
	public List<Enrollment> getByCompoundId(Long courseId, Long studentId);
	public long getStudentCountByCourseId(Long courseId);
	public List<User> enrollStudentListByCourseId(List<User> studentList, Long courseId);
	public Boolean eraseEnrollment(Long courseId);
}
