package com.charis.occam_spm_sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;

@Data
@TableName("attendances")
public class Attendance {
	@JsonSerialize(using = ToStringSerializer.class)
	private Long studentId;
	private Integer lessonId;
	private int status;
	private String note;
}
