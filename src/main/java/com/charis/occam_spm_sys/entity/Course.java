package com.charis.occam_spm_sys.entity;

import java.io.Serializable;
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
@TableName("course")
public class Course implements Serializable {
	
	@TableId(type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	private String name;
	@JsonSerialize(using = ToStringSerializer.class)
	private Long teacherId;
	private String school;
	private String department;
	private Integer scheduleWeek;
	private LocalTime scheduleStartTime;
	private LocalTime scheduleEndTime;
	private Integer duration;
	private String classroom;
	private String info;
	
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;
	
	private int status;
}
