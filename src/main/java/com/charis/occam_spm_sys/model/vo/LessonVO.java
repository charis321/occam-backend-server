package com.charis.occam_spm_sys.model.vo;

import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class LessonVO {
	private Integer lessonId;
	private Integer lessonIndex;

	private OffsetDateTime startTime;
	private OffsetDateTime endTime;
	
	private String classroom;
	private int status;
	
	private OffsetDateTime createTime;
	private OffsetDateTime updateTime;
	
	private Long courseId;
	private String courseName;
	
	private Long teacherId;
	private String teacherName;
}
