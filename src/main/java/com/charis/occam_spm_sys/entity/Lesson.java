package com.charis.occam_spm_sys.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;

@Data
@TableName("lesson")
public class Lesson {
	
	@TableId(type = IdType.AUTO)
	private Integer id;
	@JsonSerialize(using = ToStringSerializer.class)
	private Long courseId;
	@JsonSerialize(using = ToStringSerializer.class)
	private Long teacherId;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	private String duration;
	private String classroom;
	private int attendanceStatus;
	private int status;
	
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;
}
