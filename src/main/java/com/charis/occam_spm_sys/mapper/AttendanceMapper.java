package com.charis.occam_spm_sys.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.charis.occam_spm_sys.entity.Attendance;


@Mapper
public interface AttendanceMapper extends BaseMapper<Attendance> {
	
//	@Select({})
//	AttendanceCourseDetail getAttendanceCourseDetail();
}
