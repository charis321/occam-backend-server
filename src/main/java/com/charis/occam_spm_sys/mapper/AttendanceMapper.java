package com.charis.occam_spm_sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.charis.occam_spm_sys.entity.Attendance;
import com.charis.occam_spm_sys.model.vo.CourseAttendanceDetailVO;
import com.charis.occam_spm_sys.model.vo.LessonAttendanceDetailVO;
import com.charis.occam_spm_sys.model.vo.LessonAttendanceStatsVO;
import com.charis.occam_spm_sys.model.vo.StudentAttendanceStatsVO;

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
			ORDER BY e.student_id
			""")
	List<LessonAttendanceDetailVO> selectDetailsByLessonId(Integer lessonId);

	@Select("""
			SELECT
				ld.course_id AS course_id,
				ld.lesson_index ,
				ld.id AS lesson_id,
			    e.student_id,
			    u."name" AS student_name,
			    u."no" AS student_no,
			    u.school AS student_school,
			    u.department AS student_department,
			    u.sex AS student_sex,
			    a.status AS attendance_status
			FROM (
			    select *,
			    		ROW_NUMBER() OVER (
						           PARTITION BY course_id
						           ORDER BY start_time ASC
						       ) as lesson_index
						  from lessons l
			    )as ld
			left join  enrollments e on ld.course_id = e.course_id
			LEFT JOIN users u ON u.id = e.student_id
			LEFT JOIN attendances a ON (e.student_id = a.student_id AND ld.id = a.lesson_id)
			WHERE ld.course_id = #{courseId}
			order by e.student_id ,ld.lesson_index asc
			""")
	List<CourseAttendanceDetailVO> selectDetailsByCourseId(Long courseId);

	@Select("""
			select	lesson_id,
					lesson_attendance_status,
					start_time,
					end_time,
					count(1) filter(where attendance_status = 0) as absent_count,
					count(1) filter(where attendance_status = 1) as present_count,
					count(1) filter(where attendance_status = 2) as excused_count,
					count(1)as total_count
					from course_attendances
			where course_id = #{courseId}
			group by lesson_id ,start_time, end_time, lesson_attendance_status
			order by start_time asc
			""")
	List<LessonAttendanceStatsVO> selectStatsByLesson(Long courseId);

	@Select("""
			select  student_id,
					student_name,
					student_school,
					student_department,
					student_no,
					count(1) filter(where attendance_status = 0) as absent_count,
					count(1) filter(where attendance_status = 1) as present_count,
					count(1) filter(where attendance_status = 2) as excused_count,
					count(1)as total_count
					from course_attendances
			where course_id = #{courseId}
			group by student_id , student_name,student_school,student_department,student_no
			order by student_no asc
						""")
	List<StudentAttendanceStatsVO> selectStatsByStudent(Long courseId);

}
