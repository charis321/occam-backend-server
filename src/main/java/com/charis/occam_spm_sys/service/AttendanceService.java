package com.charis.occam_spm_sys.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.charis.occam_spm_sys.entity.Attendance;
import com.charis.occam_spm_sys.entity.Lesson;
import com.charis.occam_spm_sys.model.vo.CourseAttendanceDetailVO;
import com.charis.occam_spm_sys.model.vo.LessonAttendanceDetailVO;

public interface AttendanceService extends IService<Attendance> {
	public Attendance getAttendanceByCompoundId(Integer lessonId, Long studentId);
	public List<Attendance> getAttendanceListByLessonId(Integer lessonId);
	public List<Attendance> getAttendanceListByStudentId(Long studentId);
	
	public List<LessonAttendanceDetailVO> getLessonAttendanceDetail(Integer lessonId);
	public List<CourseAttendanceDetailVO> getCourseAttendanceDetail(Long courseId);
	
	
	public Boolean deleteAttendancesByLessonId(Integer lessonId);
	public Boolean deleteAttendancesByLessonId(List<Integer> lessonIds);
//	public List<Attendance> getAttendanceCourse(Long courseId);
//	public List<Attendance> getAttendancedLesson(Long courseId);
//	public Boolean buildLessonAttendaces(Lesson lesson);
//	public Boolean buildLessonAttendaces(List<Lesson> lessonList);
}
