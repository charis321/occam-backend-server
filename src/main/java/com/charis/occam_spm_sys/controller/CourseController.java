package com.charis.occam_spm_sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.charis.occam_spm_sys.common.Result;
import com.charis.occam_spm_sys.entity.Course;
import com.charis.occam_spm_sys.entity.Lesson;
import com.charis.occam_spm_sys.entity.Student;
import com.charis.occam_spm_sys.entity.User;
import com.charis.occam_spm_sys.service.CourseService;
import com.charis.occam_spm_sys.service.StudentService;
import com.charis.occam_spm_sys.service.EnrollmentService;
import com.charis.occam_spm_sys.service.LessonService;
import com.charis.occam_spm_sys.vo.CourseDetailVO;

@RestController
@RequestMapping("/api/course")
public class CourseController {
	
	private final CourseService courseService;
	private final EnrollmentService enrollmentService;
	private final StudentService studentService;
	private final LessonService lessonService;
	
	public CourseController(CourseService courseService,
							EnrollmentService enrollmentService,
							StudentService studentService,
							LessonService lessonService) {
		this.courseService = courseService;
		this.enrollmentService = enrollmentService;
		this.studentService = studentService;
		this.lessonService = lessonService;
	}
	
	@PostMapping("/save")
	public Result save(@RequestBody Course course) {
		return courseService.save(course)?Result.success("新增成功!"):Result.fail("新增失敗");
	}
	
	@GetMapping("/list")
	public Result list() {
		return Result.success(courseService.list());
	}
	
	@GetMapping("/{courseId}")
	public Result getCourseById(@PathVariable Long courseId) {
		CourseDetailVO course = courseService.getCourseDetailVO(courseId);
		return course!=null? Result.success(course): Result.fail("查無紀錄");
	}
	
	@GetMapping("/list/{teacherId}")
	public Result getCourseByTeacher(@PathVariable Long teacherId) {
		return Result.success(courseService.getCourseDetailVOByTeacherId(teacherId));
	}

	@GetMapping("/{courseId}/student/list")
	public Result getStudentsByCourseId(@PathVariable Long courseId) {
		List<Student> res = enrollmentService.getStudentListByCourseId(courseId);
		return Result.success(res);
	}
	
	@PostMapping("/{courseId}/student/saveList")
	public Result addStudentsByCourseId(@PathVariable Long courseId,@RequestBody List<Student> studentList) {
		List<Student> registerStudentList =  studentService.registerStudentList(studentList);
		Boolean res = enrollmentService.enrollStudentListByCourseId(registerStudentList, courseId);
		return res?Result.success():Result.fail();
	}
	
//	@PostMapping("/{courseId}/lesson/save")
//	public Result addNewLesson(@PathVariable Long courseId, @RequestBody Lesson lesson) {
//		return lessonService.save(lesson)? Result.success():Result.fail();
//	}
//	@PostMapping("/{courseId}/lesson/saveList")
//	public Result addNewLessonList(@PathVariable Long courseId, @RequestBody List<Lesson> lessonList) {
//		System.out.println(lessonList);
//		return lessonService.saveBatch(lessonList)? Result.success():Result.fail("fail");
//	}
//	
//	@GetMapping("/{courseId}/lesson/list")
//	public Result addNewClass(@PathVariable Long courseId) {
//		return Result.success(lessonService.getLessonListDefault(courseId));
//	}
	
	@PreAuthorize("hasRole('TEACHER')")
	@DeleteMapping("/{courseId}/erase")
	public Result eraseCourse(@PathVariable Long courseId) {
		return courseService.eraseCourse(courseId)?Result.success("成功刪除!"):Result.fail("刪除失敗，請重新再試");
	}
	
	
	
}
