package com.charis.occam_spm_sys.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.charis.occam_spm_sys.entity.Attendance;
import com.charis.occam_spm_sys.entity.Lesson;

public interface AttendanceService extends IService<Attendance> {
	public Boolean buildLessonAttendaces(Lesson lesson);
	public Boolean buildLessonAttendaces(List<Lesson> lessonList);
	public Boolean eraseLessonAttendances(Integer lessonId);
	public List<Attendance> getAttendanceCourse(Long courseId);
	
	
}
