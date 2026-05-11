package com.charis.occam_spm_sys.entity;

import java.time.OffsetDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@TableName("messages")
public class Message {
	
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	@JsonSerialize(using = ToStringSerializer.class)
	private Long senderId;
	@JsonSerialize(using = ToStringSerializer.class)
	private Long receiverId;
	
	@Size(max=255 , message="字數過多，請控制在255字內")
	private String title;
	@Size(max=500 , message="字數過多，請控制在500字內")
	private String body;
	
	private String attachment;
	private boolean noted;
	
	@TableField(fill = FieldFill.INSERT)
	private OffsetDateTime createTime;
}
