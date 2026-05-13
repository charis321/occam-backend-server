package com.charis.occam_spm_sys.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RollcallQueryDTO {
	private Integer lessonId;
	private Integer mode;

	@NotNull(message = "點名狀態 不可為空")
	@Min(value = 0, message = "點名狀態 格式不正確")
    @Max(value = 2, message = "點名狀態 格式不正確")
	private Integer status;
}
