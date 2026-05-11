package com.charis.occam_spm_sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.charis.occam_spm_sys.common.Result;
import com.charis.occam_spm_sys.entity.Course;
import com.charis.occam_spm_sys.model.dto.CourseQueryDTO;
import com.charis.occam_spm_sys.model.dto.CourseUpdateDTO;
import com.charis.occam_spm_sys.model.vo.CourseDetailVO;
import com.charis.occam_spm_sys.service.CourseService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class CourseController {

	@Autowired
	private CourseService courseService;

//	@GetMapping("/course")
//	public Result list() {
//		return Result.success(courseService.list());
//	}

	@GetMapping("/course/{courseId}")
	public Result getCourseById(@PathVariable Long courseId) {
		CourseDetailVO course = courseService.getCourseDetailVO(courseId);
		return course != null ? Result.success(course) : Result.fail("查無紀錄");
	}

	@GetMapping("/student/{studentId}/course")
	public Result getCourseListByStudentId(@Valid @PathVariable Long studentId, CourseQueryDTO query) {
		return Result.success(courseService.getCourseDetailVOByStudentId(studentId,query));
	}
	
//	@GetMapping("/course")
//	public Result listCourses(CourseQueryDTO query) {
//		log.info("正在條件查詢用戶資訊，Query: {}", query);
//		return Result.success(courseService.getCoursesByQuery(query));
//	}

	@PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
	@GetMapping("/teacher/{teacherId}/course")
	public Result getCourseByTeacher(@Valid @PathVariable Long teacherId, CourseQueryDTO query) {
		return Result.success(courseService.getCourseDetailVOByTeacherId(teacherId, query));
	}

	@PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
	@PostMapping("/course")
	public Result save(@RequestBody Course course) {
		return courseService.save(course) ? Result.success("新增成功!") : Result.fail("新增失敗");
	}

	@PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
	@PatchMapping("/course/{courseId}")
	public Result updateCourseById(@Valid @PathVariable Long courseId, @RequestBody CourseUpdateDTO dto) {
		return Result.success(courseService.updateCourse(courseId, dto));
	}

	@PreAuthorize("hasRole('TEACHER')")
	@DeleteMapping("/course/{courseId}")
	public Result eraseCourse(@PathVariable Long courseId) {
		return courseService.eraseCourse(courseId) ? Result.success("成功刪除!") : Result.fail("刪除失敗，請重新再試");
	}

}
