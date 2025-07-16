package com.charis.occam_spm_sys.vo;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.charis.occam_spm_sys.entity.Student;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;

@Data
public class UserInfoVO {
	
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	private String name;
	private String email;
	private int role;
	private String school;
	private String department;
	private int status;
	private int sex;
	
	private Boolean isVaild;
	private Student studentInfo; 
	
	private LocalDateTime createTime;
	private LocalDateTime updateTime;
}
