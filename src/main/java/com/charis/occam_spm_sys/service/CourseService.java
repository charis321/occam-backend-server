package com.charis.occam_spm_sys.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.charis.occam_spm_sys.entity.Course;
import com.charis.occam_spm_sys.vo.CourseDetailVO;

public interface CourseService extends IService<Course> {
	public List<Course> findCourseByTeacherId(Long teacherId);
	public List<Course> findCourseById(Long courseId);
	public CourseDetailVO getCourseDetailVO(Long courseId); 
	public List<CourseDetailVO> getCourseDetailVOByTeacherId(Long teacherId); 
	public Boolean eraseCourse(Long courseId);
}
