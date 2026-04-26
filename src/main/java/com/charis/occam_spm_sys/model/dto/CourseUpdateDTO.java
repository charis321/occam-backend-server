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
public class CourseUpdateDTO {
	
	@NotNull(message = "課程 ID 不能為空")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	
	@NotBlank(message = "課程名稱不能為空")
	private String name;
	
	private String school;
	private String department;
	
	@NotNull(message = "星期幾不能為空")
    @Min(value = 1, message = "星期格式不正確")
    @Max(value = 7, message = "星期格式不正確")
	private Integer scheduleWeek;
	
	@NotNull(message = "開始時間不能為空")
	private LocalTime scheduleStartTime;
    
	@NotNull(message = "結束時間不能為空")
	private LocalTime scheduleEndTime;
	
	@Positive(message = "課程時長必須為正整數")
	private Integer duration;
	
	private String classroom;
	
	@Size(max = 500, message = "備註內容過長")
	private String info;
	
	@Min(value = 0, message = "狀態格式不正確")
    @Max(value = 2, message = "狀態格式不正確")
	private int status;

}
