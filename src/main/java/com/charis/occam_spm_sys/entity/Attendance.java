package com.charis.occam_spm_sys.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;

@Data
@TableName("attendance")
public class Attendance {
	
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;
	private Long studentId;
	private Integer lessonId;
	private int status;
	private String note;
}


