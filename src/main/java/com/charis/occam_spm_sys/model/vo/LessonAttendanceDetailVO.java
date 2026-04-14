package com.charis.occam_spm_sys.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;

@Data
public class LessonAttendanceDetailVO {
	

	private Integer lessonId;
	@JsonSerialize(using = ToStringSerializer.class)
	private Long courseId;
	private String courseName;
	
	@JsonSerialize(using = ToStringSerializer.class)
	private Long studentId;
	private String studentName;
	private String studentSchool;
	private String studentDepartment;
	private String studentNo;
	private int studentSex;
	
	private Integer status;
	private String note;
}
