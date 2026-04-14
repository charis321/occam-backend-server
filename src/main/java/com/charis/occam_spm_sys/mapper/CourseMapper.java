package com.charis.occam_spm_sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.charis.occam_spm_sys.entity.Course;
import com.charis.occam_spm_sys.model.vo.CourseDetailVO;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {

	@Select("""
			SELECT
				c.*,
				u.name AS teacher_name,
			   	COUNT(e.student_id) AS student_count
			FROM courses c
			LEFT JOIN users u ON c.teacher_id = u.id
			LEFT JOIN enrollments e ON c.id = e.course_id
			WHERE c.id = #{courseId}
			GROUP BY c.id, u.name;
					""")
	public CourseDetailVO selectDetailById(Long courseId);

	@Select("""
			SELECT
				c.*,
				u.name AS teacher_name,
			   	COUNT(e.student_id) AS student_count
			FROM courses c
			LEFT JOIN users u ON c.teacher_id = u.id
			LEFT JOIN enrollments e ON c.id = e.course_id
			WHERE c.teacher_id = #{teacherId}
			GROUP BY c.id, u.name;
					""")
	public List<CourseDetailVO> selectDetailsByTeacherId(Long teacherId);

	@Select("""
			SELECT
			    c.*,
			    u.name AS teacher_name,
			    stat.total_students AS student_count
			FROM enrollments e
			JOIN courses c ON e.course_id = c.id
			LEFT JOIN users u ON c.teacher_id = u.id
			LEFT JOIN (
			    SELECT course_id, COUNT(student_id) AS total_students
			    FROM enrollments
			    GROUP BY course_id
			) stat ON c.id = stat.course_id
			WHERE e.student_id = #{studentId};
					""")
	public List<CourseDetailVO> selectDetailsByStudentId(Long studentId);

}
