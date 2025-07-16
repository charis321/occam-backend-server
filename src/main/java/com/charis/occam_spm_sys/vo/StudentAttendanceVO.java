package com.charis.occam_spm_sys.vo;

import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.charis.occam_spm_sys.entity.Attendance;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class StudentAttendanceVO {
	@TableId(type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	private String name;
	private String no;
	private String school;
	private String department;
	private int sex;
	private int status;
	
	private Long courseId;
	private String course_name;
	private List<Attendance> attendance_table;
	
}
