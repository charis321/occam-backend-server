package com.charis.occam_spm_sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.charis.occam_spm_sys.entity.Course;
import com.charis.occam_spm_sys.service.CourseService;

@RestController
@RequestMapping("/api/teacher")
@CrossOrigin(value = "*")
public class TeacherController {
	
	@Autowired
	private CourseService courseService;
	
	@GetMapping("/{userId}/course/list")
	public List<Course> getCourseListByTeacherId(@PathVariable Long userId) {
		return courseService.findCourseByTeacherId(userId);
	}
	
//	@GetMapping("/{userId}/course/{}")
//	public List<Course> getCourseByTeacherId(@PathVariable int userId) {
//		return courseService.findCourseByTeacherId(userId);
//	}
}
