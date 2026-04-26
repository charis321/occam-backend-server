package com.charis.occam_spm_sys.model.vo;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;

@Data
public class LessonAttendanceStatsVO {
	
	private Integer lessonId;
	private String lessonIndex;
	private OffsetDateTime startTime;
	private OffsetDateTime endTime;

	private Integer presentCount;
	private Integer excusedCount;
	private Integer absentCount;
}
