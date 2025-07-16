package com.charis.occam_spm_sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.charis.occam_spm_sys.entity.Lesson;

@Mapper
public interface LessonMapper extends BaseMapper<Lesson> {	
	
	@Select("SELECT * FROM lesson WHERE teacher_id = #{teacherId}")
    List<Lesson> findByTeacherId(Long teacherId);
	
	@Select("SELECT * FROM lesson WHERE course_id = #{courseId} ORDER BY date ASC, start_time ASC")
	List<Lesson> findByCourseIdDefault(Long courseId);
	
//	@Insert({
//	    "<script>",
//	    "INSERT INTO user (name, email) VALUES",
//	    "<foreach collection='list' item='item' separator=','>",
//	    "(#{item.name}, #{item.email})",
//	    "</foreach>",
//	    "</script>"
//	})
//	List<Lesson> findByCourseIdDefault(Long courseId);
}
