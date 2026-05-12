package com.charis.occam_spm_sys.model.vo;

import java.time.OffsetDateTime;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RollcallVO {
	
	@NotNull(message = "課堂ID 不可為空")
	private Integer lessonId;
	private int mode;

	private Integer rotationTime;
	
	private OffsetDateTime nextRotationTime; 
	private OffsetDateTime startTime;
	private OffsetDateTime endTime;

	@NotNull(message = "點名狀態 不可為空")
	@Min(value = 0, message = "點名狀態 格式不正確")
	@Max(value = 2, message = "點名狀態 格式不正確")
	private Integer status;
	private String code;
}
