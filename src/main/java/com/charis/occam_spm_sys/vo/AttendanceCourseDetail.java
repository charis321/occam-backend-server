package com.charis.occam_spm_sys.vo;

import java.time.LocalTime;

import lombok.Data;

@Data
public class AttendanceCourseDetail {
	
	private Long id;
	private Long studentId;
	private Integer lessonId;
	private int status;
	private String note;
	
	private Long courseId;
	private Long courseName;
	private Long courseSchedule;
	private Long teacherId;
	private Integer scheduleWeek;
	private LocalTime scheduleStartTime;
	private LocalTime scheduleEndTime;
	private Integer duration;
	private String classroom;
	
	private String studentName;
	private String studentSchoool;
	private String studentDepartment;
	private String studentNo;
	private int studentSex;
	
	private Integer lessonKey;
	
	
}
