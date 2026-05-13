package com.charis.occam_spm_sys.model.vo;

import java.time.OffsetDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;

@Data
public class MessageVO {
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	
	@JsonSerialize(using = ToStringSerializer.class)
	private Long senderId;
	private String senderName;
	private int senderRole;
	private String senderEmail;
	private String senderNo;
	
	
	@JsonSerialize(using = ToStringSerializer.class)
	private Long receiverId;
	private String receiverName;
	private int receiverRole;
	private String receiverEmail;
	private String receiverNo;
	
	private String title;
	private String body;
	private boolean noted = false; 

	private OffsetDateTime createTime;
}
