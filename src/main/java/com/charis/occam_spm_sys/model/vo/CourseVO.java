package com.charis.occam_spm_sys.model.vo;

import java.time.LocalTime;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;

@Data
public class CourseVO {
	
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	private String name;
	
	private String school;
	private String department;
	private Integer scheduleWeek;
	private LocalTime scheduleStartTime;
	private LocalTime scheduleEndTime;
	private Integer duration;
	private String classroom;
	private String info;
	
	@JsonSerialize(using = ToStringSerializer.class)
	private Long teacherId;
	private Long teacherName;
	
	private Integer studentCount; 
	
	private OffsetDateTime createTime;
	private OffsetDateTime updateTime;

	private int status;
}
