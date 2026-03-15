package com.charis.occam_spm_sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("attendances")
public class Attendance {
	private Long studentId;
	private Integer lessonId;
	private int status;
	private String note;
}
