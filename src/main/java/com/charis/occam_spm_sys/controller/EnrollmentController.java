package com.charis.occam_spm_sys.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.charis.occam_spm_sys.common.Result;
import com.charis.occam_spm_sys.entity.Enrollment;
import com.charis.occam_spm_sys.entity.User;
import com.charis.occam_spm_sys.service.EnrollmentService;
import com.charis.occam_spm_sys.service.UserService;

@RestController
@RequestMapping("/api/course/{courseId}/enrollment")
public class EnrollmentController {

	@Autowired
	private EnrollmentService enrollmentService;
	
	@Autowired
	private UserService userService;

	@GetMapping
	public Result getEnrollment(@PathVariable Long courseId) {
		return Result.success(enrollmentService.getStudentListByCourseId(courseId));
	}
	
	@PostMapping
	public Result addEnrollmentBatch(@PathVariable Long courseId,
									 @RequestBody List<User> studentList) {
		List<User> invalidStudents = enrollmentService.enrollStudentListByCourseId(studentList, courseId);
		
		if (invalidStudents.isEmpty()) {
	        return Result.success("批量新增成功");
	    }
		return Result.fail(400, "批量新增失敗: 找不到指定的學生資料", invalidStudents);
	}


//	
//	@PostMapping("/saveList")
//	public Result addNewStudentList(@RequestBody List<Student> studentList) {
//		//List<Student> failBatch = studentService.saveOrFailBatch(studentList)
////		List<List<Student>> failedBatches = BatchInsertUtil.batchInsertWithSkip(
////		        students,
////		        batch -> studentService.saveBatch(batch)
////		    );
////
////		    if (!failedBatches.isEmpty()) {
////		        System.out.println("⚠️ 有批次插入失敗，共 " + failedBatches.size() + " 批");
////		        // 你可以進一步儲存失敗批次到檔案、重試、回報等等
////		    }
//		return studentService.saveBatch(studentList)?Result.success("新增成功"):Result.fail("新增失敗");
//	}

}
