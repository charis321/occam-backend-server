package com.charis.occam_spm_sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.charis.occam_spm_sys.common.Result;
import com.charis.occam_spm_sys.entity.Lesson;
import com.charis.occam_spm_sys.entity.Student;
import com.charis.occam_spm_sys.service.LessonService;

@RestController
@RequestMapping("/api/course/{courseId}/lesson")
public class LessonController {
	
	@Autowired
	private LessonService lessonService;
	
	@GetMapping
	public Result getLesson(@PathVariable Long courseId) {
		return Result.success(lessonService.getLessonListDefault(courseId));
	}
	
	@DeleteMapping
	public Result deleteLesson(@RequestBody Integer lessonId) {
		return lessonService.deleteLesson(lessonId)?Result.success():Result.fail();
	}
	
	@PostMapping
	public Result createLesson(@RequestBody Lesson lesson) {
		return lessonService.save(lesson)?Result.success("新增成功"):Result.fail("新增失敗");
	}
	
	
	@PostMapping("/batch")
	public Result creareLesson(@RequestBody List<Lesson> lessonList) {
		return lessonService.saveBatch(lessonList)? Result.success():Result.fail();
	}
	

	@PostMapping("/attendance")
	public Result startAttendanceRegister(@RequestBody Lesson lesson) {
		return lessonService.startRegistering(lesson)?Result.success():Result.fail();
	}
}
