package com.charis.occam_spm_sys.entity;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;

@Data
@TableName("enrollments")
public class Enrollment {
	
	@JsonSerialize(using = ToStringSerializer.class)
	private Long courseId;
	@JsonSerialize(using = ToStringSerializer.class)
	private Long studentId;
	
	private int status;
	private String note;
	
	@TableField(fill = FieldFill.INSERT)
	private OffsetDateTime createTime;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private OffsetDateTime updateTime;
}
