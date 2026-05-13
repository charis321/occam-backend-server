package com.charis.occam_spm_sys.model.vo;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;

@Data
public class LessonDetailVO {
	
	private Integer id;
	private Integer lessonIndex;
	
	@JsonSerialize(using = ToStringSerializer.class)
	private Long teacherId;
	private String teacherName;
	
	private OffsetDateTime startTime;
	private OffsetDateTime endTime;
	
	private String classroom;
	private int rollcallStatus;
	private int status;
	
	private OffsetDateTime createTime;
	private OffsetDateTime updateTime;
	
	@JsonSerialize(using = ToStringSerializer.class)
	private Long courseId;
	private String courseName;	
}
