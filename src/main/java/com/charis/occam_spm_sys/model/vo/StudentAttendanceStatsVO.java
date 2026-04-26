package com.charis.occam_spm_sys.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;

@Data
public class StudentAttendanceStatsVO {
	
	@JsonSerialize(using = ToStringSerializer.class)
	private Long studentId;
	private String studentName;
	private String studentNo;
	private String studentSchool;
	private String studentDepartment;
	
	
	private Integer presentCount;
	private Integer excusedCount;
	private Integer absentCount;
}
