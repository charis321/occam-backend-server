package com.charis.occam_spm_sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.charis.occam_spm_sys.entity.Attendance;
import com.charis.occam_spm_sys.entity.Lesson;
import com.charis.occam_spm_sys.model.dto.RollcallDTO;
import com.charis.occam_spm_sys.model.vo.LessonDetailVO;
import com.charis.occam_spm_sys.service.AttendanceService;
import com.charis.occam_spm_sys.service.LessonService;

@RestController
@RequestMapping("/api")
public class LessonController {

	@Autowired
	private LessonService lessonService;
	
	@Autowired
	private AttendanceService attendanceService;


	@GetMapping("/lesson/{lessonId}")
	public Result getLesson(@PathVariable Integer lessonId) {
		return Result.success(lessonService.getById(lessonId));
	}
	
	@GetMapping("/lesson/teacher/{teacherId}")
	public Result getLessonsByTeacher(@PathVariable Long teacherId) {
		return Result.success(lessonService.getLessonDetailsByTeacherId(teacherId));
	}
	
	@GetMapping("/lesson/student/{studentId}")
	public Result getLessonsByStudent(@PathVariable Long studentId) {
		return Result.success(lessonService.getLessonDetailsByStudentId(studentId));
	}

	@GetMapping("/course/{courseId}/lesson")
	public Result getLessonsByCourse(@PathVariable Long courseId) {
		return Result.success(lessonService.getLessonDetailsByCourseId(courseId));
	}
	
	@GetMapping("/lesson/{lessonId}/attendance/{studentId}")
	public Result getLessonAttendanceForStudent(@PathVariable Integer lessonId,
												@PathVariable Long studentId) {
		LessonDetailVO lesson = lessonService.getLessonDetailById(lessonId);
		Attendance attendance = attendanceService.getAttendanceByCompoundId(lessonId, studentId);
		
		Map<String, Object> data = new HashMap<>();
		data.put("lesson", lesson);
		data.put("attendance", attendance);

		return Result.success(data);
	}

//	@PutMapping("/lesson/{lessonId}")
//	public Result updateLesson( @PathVariable Integer lessonId,
//								@RequestBody Map<String, Object> updates) {
//		Lesson lesson = new Lesson();
//		
//		
//		Lesson lesson = lessonService.update()
//		
//
//		
//		Map<String,Object> data =  new HashMap<>();
//		data.put("lesson", lesson);
//		data.put("attendance", attendanceList);
//		
//		return Result.success(data);
//	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
	@PatchMapping("/lesson/{lessonId}/rollcall")
	public Result updateLessonAttendanceStatus(@PathVariable Integer lessonId,
											   @RequestBody  RollcallDTO rollcall) {
		return Result.success(lessonService.handleRollcall(lessonId, rollcall));
	}

	@PostMapping("/course/{courseId}/lesson")
	public Result createLesson(@RequestBody Lesson lesson) {
		return lessonService.save(lesson) ? Result.success("新增成功") : Result.fail("新增失敗");
	}

	@PostMapping("/course/{courseId}/lesson/batch")
	public Result createLessonBatch(@RequestBody List<Lesson> lessonList) {
		return lessonService.saveBatch(lessonList) ? Result.success("新增成功") : Result.fail("新增失敗");
	}

	@DeleteMapping("/lesson/{lessonId}")
	public Result deleteLesson(@PathVariable Integer lessonId) {
		return lessonService.deleteLesson(lessonId) ? Result.success() : Result.fail();
	}

//
//	@PostMapping("/attendance")
//	public Result startAttendanceRegister(@RequestBody Lesson lesson) {
//		return lessonService.startRegistering(lesson)?Result.success():Result.fail();
//	}

}
