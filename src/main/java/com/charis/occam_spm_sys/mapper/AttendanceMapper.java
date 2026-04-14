package com.charis.occam_spm_sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.charis.occam_spm_sys.entity.Attendance;
import com.charis.occam_spm_sys.model.vo.LessonAttendanceDetailVO;

@Mapper
public interface AttendanceMapper extends BaseMapper<Attendance> {

	@Select("""
			SELECT
			    e.student_id,
			    u."name" AS student_name,
			    u."no" AS student_no,
			    u.school AS student_school,
			    u.department AS student_department,
			    u.sex AS student_sex,
			    a.status,
			    COALESCE(a.lesson_id, #{lessonId}) AS lesson_id
			FROM enrollments e
			LEFT JOIN attendances a ON e.student_id = a.student_id AND a.lesson_id = #{lessonId}
			LEFT JOIN users u ON u.id = e.student_id
			WHERE e.course_id = (SELECT course_id FROM lessons WHERE id = #{lessonId})
						""")
	List<LessonAttendanceDetailVO> selectDetailsByLessonId(Integer lessonId);
	
//	@Select("""
//			SELECT
//			    e.student_id,
//			    u."name" AS student_name,
//			    u."no" AS student_no,
//			    u.school AS student_school,
//			    u.department AS student_department,
//			    u.sex AS student_sex,
//			    a.id AS attendance_id,
//			    a.status,
//			    COALESCE(a.lesson_id, #{lessonId}) AS lesson_id
//			FROM enrollments e
//			LEFT JOIN attendances a ON e.student_id = a.student_id AND a.lesson_id = #{lessonId}
//			LEFT JOIN users u ON u.id = e.student_id
//			WHERE e.course_id = (SELECT course_id FROM lessons WHERE id = #{lessonId})
//						""")
//	List<LessonAttendanceDetailVO> selectDetailsByLessonId(Long courseId);

}
