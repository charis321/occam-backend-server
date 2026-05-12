package com.charis.occam_spm_sys.model.dto;

import java.time.LocalTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class CourseNewDTO {

	private String name;
	@JsonSerialize(using = ToStringSerializer.class)
	private Long teacherId;
	
	private String school;
	private String department;
	
	@NotBlank(message = "星期 不可為空")
	@Min(value = 1, message = "星期 格式不正確")
	@Max(value = 7, message = "星期 格式不正確")
	private Integer scheduleWeek;
	
	
	private LocalTime scheduleStartTime;
	private LocalTime scheduleEndTime;
	
	private Integer duration;
	private String classroom;
	private String info;
	
	@Min(value = 0, message = "狀態 格式不正確")
    @Max(value = 2, message = "狀態 格式不正確")
	private Integer status = 0;

}
