package com.charis.occam_spm_sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.charis.occam_spm_sys.common.Result;
import com.charis.occam_spm_sys.entity.Attendance;
import com.charis.occam_spm_sys.entity.Lesson;
import com.charis.occam_spm_sys.service.AttendanceService;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
	
	@Autowired
	private AttendanceService attendanceService;
	
	@GetMapping
	public Result getAttendance() {
		return Result.success(attendanceService.list());
	}
	
	@GetMapping("/by-course")
	public Result getAttendanceCourse(@RequestParam Long courseId) {
		return null;
	}
	
	
	@PostMapping
	public Result addAttendance(@RequestBody Attendance attendance) {
		return attendanceService.save(attendance)?Result.success():Result.fail();
	}
	
}
