package com.charis.occam_spm_sys.model.dto;

import java.time.OffsetDateTime;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MessageDTO {

	@NotNull(message= "寄信人ID 不可為空")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long senderId;
	@NotNull(message= "收信人ID 不可為空")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long receiverId;
	
	@Size(max=255 , message="標題字數過多，請控制在255字內")
	private String title;
	@Size(max=500 , message="本文字數過多，請控制在500字內")
	private String body;
	
	private MultipartFile attachmentFile;
	
	private OffsetDateTime createTime;
	
	
	private Boolean noted = false;

}
