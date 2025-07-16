package com.charis.occam_spm_sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.charis.occam_spm_sys.common.Result;
import com.charis.occam_spm_sys.entity.Student;
import com.charis.occam_spm_sys.service.StudentService;

@RestController
@RequestMapping("/api/student")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@PostMapping("/save")
	public Result addNewStudent(@RequestBody Student student) {
		return studentService.save(student)?Result.success("新增成功"):Result.fail("新增失敗");
	}
	
	@PostMapping("/saveList")
	public Result addNewStudentList(@RequestBody List<Student> studentList) {
		//List<Student> failBatch = studentService.saveOrFailBatch(studentList)
//		List<List<Student>> failedBatches = BatchInsertUtil.batchInsertWithSkip(
//		        students,
//		        batch -> studentService.saveBatch(batch)
//		    );
//
//		    if (!failedBatches.isEmpty()) {
//		        System.out.println("⚠️ 有批次插入失敗，共 " + failedBatches.size() + " 批");
//		        // 你可以進一步儲存失敗批次到檔案、重試、回報等等
//		    }
		return studentService.saveBatch(studentList)?Result.success("新增成功"):Result.fail("新增失敗");
	}
}
