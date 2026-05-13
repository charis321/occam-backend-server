package com.charis.occam_spm_sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.charis.occam_spm_sys.entity.Rollcall;
import com.charis.occam_spm_sys.model.vo.RollcallDetailVO;

@Mapper
public interface RollcallMapper extends BaseMapper<Rollcall>{
	@Select("""
			select l.id as lesson_id,
					l.start_time ,
					l.end_time ,
					c.id as course_id,
					c."name" as course_name,
					r."mode" as rollcall_mode
				from lessons l
				left join rollcalls r
				on l.id = r.lesson_id 
				left join enrollments e 
				on e.course_id = l.course_id 
				left join courses c  
				on e.course_id = c.id
				where r.status = 1 and e.student_id = #{studentId}
			""")
	public List<RollcallDetailVO> selectRollcallingByStudentId(Long studentId);
	
	@Select("""
			select l.id as lesson_id,
					l.start_time ,
					l.end_time ,
					c.id as course_id,
					c."name" as course_name,
					r."mode" as rollcall_mode
				from lessons l
				left join rollcalls r
				on l.id = r.lesson_id 
				left join courses c  
				on l.course_id = c.id
				where r.status = 1 and c.teacher_id = #{teacherId}
			""")
	public List<RollcallDetailVO> selectRollcallingByTeacherId(Long teacherId);
}
