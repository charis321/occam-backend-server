package com.charis.occam_spm_sys.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;

@Data
@TableName("enrollment")
public class Enrollment {
	
	@JsonSerialize(using = ToStringSerializer.class)
	private Long courseId;
	@JsonSerialize(using = ToStringSerializer.class)
	private Long studentId;
	
	private int status;
	private String note;
	
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;
}
