package com.charis.occam_spm_sys.model.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;

@Data
public class CourseDetailVO implements Serializable {
	
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	private String name;
	private String school;
	private String department;
	
	@JsonSerialize(using = ToStringSerializer.class)
	private Long teacherId;
	private String teacherName;
	
	private Integer scheduleWeek;
	private LocalTime scheduleStartTime;
	private LocalTime scheduleEndTime;
	private Integer duration;
	private String classroom;
	
	@JsonSerialize(using = ToStringSerializer.class)
	private long studentCount;
	private OffsetDateTime createTime;
	private OffsetDateTime updateTime;
	private String info;
	private int status;
}
