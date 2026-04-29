package com.charis.occam_spm_sys.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.charis.occam_spm_sys.entity.Course;
import com.charis.occam_spm_sys.model.dto.CourseQueryDTO;
import com.charis.occam_spm_sys.model.dto.CourseUpdateDTO;
import com.charis.occam_spm_sys.model.vo.CourseDetailVO;

public interface CourseService extends IService<Course> {
	public List<Course> getCoursesByTeacherId(Long teacherId);
//	public List<Course> getCoursesById(Long courseId);
	
	public CourseDetailVO getCourseDetailVO(Long courseId); 
	
//	public List<CourseDetailVO> getCoursesByQuery(CourseQueryDTO query);
	public List<CourseDetailVO> getCourseDetailVOByTeacherId(Long teacherId ,CourseQueryDTO query); 
	public List<CourseDetailVO> getCourseDetailVOByStudentId(Long studentId, CourseQueryDTO query); 

	public Boolean updateCourse(Long courseId, CourseUpdateDTO dto);
	public Boolean eraseCourse(Long courseId);
}
