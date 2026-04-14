package com.charis.occam_spm_sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.charis.occam_spm_sys.entity.Lesson;
import com.charis.occam_spm_sys.model.vo.LessonDetailVO;

@Mapper
public interface LessonMapper extends BaseMapper<Lesson> {

	@Select("SELECT * FROM lessons WHERE teacher_id = #{teacherId}")
	List<Lesson> selcetByTeacherId(Long teacherId);

	@Select("SELECT * FROM lessons WHERE course_id = #{courseId} ORDER BY start_time ASC")
	List<Lesson> selcetByCourseIdDefault(Long courseId);

//	@Select("SELECT l.*, u.name as teacher_name FROM lesson as l LEFT JOIN user as u ON l.teacher_id = u.id")
//	List<Lesson> getLesson(Integer lessonId);

	@Select("""
			SELECT l.*,
			       u.name as teacher_name,
			       c."name" as course_name,
			       ROW_NUMBER() OVER (
			           PARTITION BY l.course_id
			           ORDER BY l.start_time ASC
			       ) as lesson_index
			FROM lessons l
			LEFT JOIN users u ON l.teacher_id = u.id
			LEFT JOIN courses c ON l.course_id = c.id
			ORDER BY l.course_id ASC, l.start_time ASC
			WHERE l.id = #{lessonId}
			""")
	LessonDetailVO selectDetailById(Integer lessonId);
	
	@Select("""
			SELECT l.*,
			       u.name as teacher_name,
			       c."name" as course_name,
			       ROW_NUMBER() OVER (
			           PARTITION BY l.course_id
			           ORDER BY l.start_time ASC
			       ) as lesson_index
			FROM lessons l
			LEFT JOIN users u ON l.teacher_id = u.id
			LEFT JOIN courses c ON l.course_id = c.id
			WHERE l.course_id = #{courseId}
			ORDER BY l.course_id ASC, l.start_time ASC
			""")
	List<LessonDetailVO> selectDetailsByCourseId(Long courseId);
	
	@Select("""
			SELECT  e.course_id ,
			        e.student_id,
			        l.*,
			        c."name" as course_name
			FROM enrollments e
			INNER JOIN courses c ON c.id = e.course_id 
			LEFT JOIN lessons l ON e.course_id = l.course_id 
			WHERE e.student_id = #{studentId}
			""")
	List<LessonDetailVO> selectDetailsByStudentId(Long studentId);
	
	@Select("""
			SELECT l.*,
			       u.name as teacher_name,
			       c."name" as course_name,
			       ROW_NUMBER() OVER (
			           PARTITION BY l.course_id
			           ORDER BY l.start_time ASC
			       ) as lesson_index
			FROM lessons l
			LEFT JOIN users u ON l.teacher_id = u.id
			LEFT JOIN courses c ON l.course_id = c.id
			WHERE l.teacher_id = #{teacherId}
			ORDER BY l.course_id ASC, l.start_time ASC
			""")
	List<LessonDetailVO> selectDetailsByTeacherId(Long teacherId);
}
