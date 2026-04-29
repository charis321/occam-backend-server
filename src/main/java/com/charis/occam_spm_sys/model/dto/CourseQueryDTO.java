package com.charis.occam_spm_sys.model.dto;

import java.time.LocalTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CourseQueryDTO {
	
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	
	private String name;
	
	private String school;
	private String department;
	
    @Min(value = 1, message = "星期格式不正確")
    @Max(value = 7, message = "星期格式不正確")
	private Integer scheduleWeek;
	
	private LocalTime scheduleStartTime;
	private LocalTime scheduleEndTime;
	
	@Positive(message = "課程時長必須為正整數")
	private Integer duration;
	
	private String classroom;
	
	@Size(max = 500, message = "備註內容過長")
	private String info;
	
	@Min(value = 0, message = "狀態格式不正確")
    @Max(value = 2, message = "狀態格式不正確")
	private Integer status;
}