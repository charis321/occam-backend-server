package com.charis.occam_spm_sys.model.vo;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;

@Data
public class RollcallDetailVO {
	
	private Integer lessonId;
	@JsonSerialize(using = ToStringSerializer.class)
	private Long courseId;
	private String courseName;
	private OffsetDateTime startTime; 
	private OffsetDateTime endTime; 
	private Integer rollcallMode;
	
}
