package com.charis.occam_spm_sys.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
@Data
public class AttendanceDTO {

	@JsonSerialize(using = ToStringSerializer.class)
	private Long studentId;
	private Integer lessonId;
	
	@Min(value = 0, message = "點名請求狀態 格式不正確")
    @Max(value = 2, message = "點名請求狀態 格式不正確")
	private int status;

	private String note;
}
