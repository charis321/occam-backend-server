package com.charis.occam_spm_sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.charis.occam_spm_sys.common.Result;
import com.charis.occam_spm_sys.entity.Attendance;
import com.charis.occam_spm_sys.service.AttendanceService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class AttendanceController {

	@Autowired
	private AttendanceService attendanceService;

	@GetMapping("/attendance")
	public Result getAttendance() {
		return Result.success(attendanceService.list());
	}

	@GetMapping("/lesson/{lessonId}/attendance")
	public Result getLessonAttendanceDetail(@PathVariable Integer lessonId) {
		return Result.success(attendanceService.getLessonAttendanceDetail(lessonId));
	}

	@GetMapping("/course/{courseId}/attendance")
	public Result getCourseAttendanceDetail(@PathVariable Long courseId) {
		return Result.success(attendanceService.getCourseAttendanceDetail(courseId));
	}

	@GetMapping("/course/{courseId}/attendance/stats/student")
	public Result getCourseAttendanceStatsByStudent(@PathVariable Long courseId) {
		return Result.success(attendanceService.getStudentAttendanceStats(courseId));
	}

	@GetMapping("/course/{courseId}/attendance/stats/lesson")
	public Result getCourseAttendanceStatsByLesson(@PathVariable Long courseId) {
		return Result.success(attendanceService.getLessonAttendanceStats(courseId));
	}

	@PostMapping("/attendance")
	public Result addAttendance(@RequestBody Attendance attendance) {
		return attendanceService.save(attendance)?Result.success():Result.fail();
	}

	@PutMapping("/attendance")
	public Result updateAttendance(@RequestBody Attendance attendance) {
		return attendanceService.saveOrUpdateAttendance(attendance)?Result.success():Result.fail();
	}
	
}
