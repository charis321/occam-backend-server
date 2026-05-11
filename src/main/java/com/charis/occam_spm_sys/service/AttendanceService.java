package com.charis.occam_spm_sys.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.charis.occam_spm_sys.entity.Attendance;
import com.charis.occam_spm_sys.model.dto.AttendanceDTO;
import com.charis.occam_spm_sys.model.dto.AttendanceVerifyDTO;
import com.charis.occam_spm_sys.model.vo.CourseAttendanceDetailVO;
import com.charis.occam_spm_sys.model.vo.LessonAttendanceDetailVO;
import com.charis.occam_spm_sys.model.vo.LessonAttendanceStatsVO;
import com.charis.occam_spm_sys.model.vo.StudentAttendanceStatsVO;

public interface AttendanceService extends IService<Attendance> {
	public Attendance getAttendanceByCompoundId(Integer lessonId, Long studentId);
	public List<Attendance> getAttendanceListByLessonId(Integer lessonId);
	public List<Attendance> getAttendanceListByStudentId(Long studentId);
	
	public List<StudentAttendanceStatsVO> getStudentAttendanceStats(Long courseId);
	public List<LessonAttendanceStatsVO> getLessonAttendanceStats(Long courseId);
	
	public List<LessonAttendanceDetailVO> getLessonAttendanceDetail(Integer lessonId);
	public List<CourseAttendanceDetailVO> getCourseAttendanceDetail(Long courseId);
	
	public void saveOrUpdateAttendance(AttendanceDTO attendanceDTO);
	public void verifyAndSaveAttendance(AttendanceVerifyDTO verifyDTO);
	
	public Boolean deleteAttendancesByLessonId(Integer lessonId);
	public Boolean deleteAttendancesByLessonId(List<Integer> lessonIds);
//	public List<Attendance> getAttendanceCourse(Long courseId);
//	public List<Attendance> getAttendancedLesson(Long courseId);
//	public Boolean buildLessonAttendaces(Lesson lesson);
//	public Boolean buildLessonAttendaces(List<Lesson> lessonList);
}
