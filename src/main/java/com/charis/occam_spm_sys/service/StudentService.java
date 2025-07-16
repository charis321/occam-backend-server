package com.charis.occam_spm_sys.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.charis.occam_spm_sys.entity.Student;

public interface StudentService extends IService<Student> {
	public Student checkAndAddStudent(Student student);
	public boolean saveList(List<Student> studentList);
	public List<Student> registerStudentList(List<Student> studentList);
	public Student checkStudnetVaild(Long studentId);
	public Student checkStudnetVaildByUserId(Long userId);
}
