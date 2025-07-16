package com.charis.occam_spm_sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.charis.occam_spm_sys.entity.Student;
import com.charis.occam_spm_sys.mapper.StudentMapper;
import com.charis.occam_spm_sys.service.StudentService;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService{


	@Override
	public boolean saveList(List<Student> studentList) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Student> registerStudentList(List<Student> studentList) {
		List<Student> registerStudentList = new ArrayList<Student>(); 
		for(Student s:studentList) {
			Student student = this.checkAndAddStudent(s);
			registerStudentList.add(student);
		}
		System.out.println(registerStudentList);
		return registerStudentList;
	}

//	@Override
//	public boolean saveList(List<Student> studentList) {
//		List<Student> noRegisterStudentList = new ArrayList<Student>(); 
//		for(Student s: studentList) {
//			Student student = this.checkStudentExist(s);
//				noRegisterStudentList.add(s);
//			}
//		}
//		return this.saveBatch(noRegisterStudentList);
//	}
//	
	@Override
	public Student checkAndAddStudent(Student student) {
		LambdaQueryWrapper<Student> lambdaQueryWrapper = new LambdaQueryWrapper<Student>();
		lambdaQueryWrapper.eq(Student::getNo, student.getNo())
						  .eq(Student::getSchool, student.getSchool());
		Student registeredStudent = this.getOne(lambdaQueryWrapper);
		if(registeredStudent==null) {
			this.save(student);
			return student;
		}else {
			return registeredStudent;
		}
	}

	@Override
	public Student checkStudnetVaild(Long studentId) {
		return Optional.ofNullable(this.getById(studentId))
					   .filter(s -> s.getUserId() != null)
					   .orElse(null);
	}

	@Override
	public Student checkStudnetVaildByUserId(Long userId) {
		LambdaQueryWrapper<Student> lambdaQueryWrapper = new LambdaQueryWrapper<Student>();
		lambdaQueryWrapper.eq(Student::getUserId, userId);
		return this.getOne(lambdaQueryWrapper);

	}
	
	

}
