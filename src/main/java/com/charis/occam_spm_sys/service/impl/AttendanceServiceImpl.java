package com.charis.occam_spm_sys.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.charis.occam_spm_sys.entity.Attendance;
import com.charis.occam_spm_sys.entity.Rollcall;
import com.charis.occam_spm_sys.exception.BusinessException;
import com.charis.occam_spm_sys.mapper.AttendanceMapper;
import com.charis.occam_spm_sys.model.dto.AttendanceDTO;
import com.charis.occam_spm_sys.model.dto.AttendanceVerifyDTO;
import com.charis.occam_spm_sys.model.vo.CourseAttendanceDetailVO;
import com.charis.occam_spm_sys.model.vo.LessonAttendanceDetailVO;
import com.charis.occam_spm_sys.model.vo.LessonAttendanceStatsVO;
import com.charis.occam_spm_sys.model.vo.StudentAttendanceStatsVO;
import com.charis.occam_spm_sys.service.AttendanceService;
import com.charis.occam_spm_sys.service.RollcallService;

@Service
public class AttendanceServiceImpl extends ServiceImpl<AttendanceMapper, Attendance> implements AttendanceService {
	
	@Autowired
	private RollcallService rollcallService;
	
	@Override
	public Boolean deleteAttendancesByLessonId(Integer lessonId) {
		return this.lambdaUpdate().eq(Attendance::getLessonId, lessonId).remove();
	}

	@Override
	public Boolean deleteAttendancesByLessonId(List<Integer> lessonIds) {
		if (CollectionUtils.isEmpty(lessonIds))
			return true;

		return this.lambdaUpdate().in(Attendance::getLessonId, lessonIds).remove();
	}

	@Override
	public Attendance getAttendanceByCompoundId(Integer lessonId, Long studentId) {
		return this.lambdaQuery()
					.eq(Attendance::getStudentId, studentId)
					.eq(Attendance::getLessonId, lessonId)
					.one();
	}

	@Override
	public List<Attendance> getAttendanceListByLessonId(Integer lessonId) {
		return this.lambdaQuery().eq(Attendance::getLessonId, lessonId)
								.orderByAsc(Attendance::getStudentId)
								.list();
	}

	@Override
	public List<Attendance> getAttendanceListByStudentId(Long studentId) {
		return this.lambdaQuery().eq(Attendance::getStudentId, studentId).list();
	}

	@Override
	public List<LessonAttendanceDetailVO> getLessonAttendanceDetail(Integer lessonId) {
		return baseMapper.selectDetailsByLessonId(lessonId);
	}

	@Override
	public List<CourseAttendanceDetailVO> getCourseAttendanceDetail(Long courseId) {
		return baseMapper.selectDetailsByCourseId(courseId);
	}

	@Override
	@Transactional
	public void saveOrUpdateAttendance(AttendanceDTO attendanceDTO) {

		var wrapper = new LambdaUpdateWrapper<Attendance>();

		wrapper.eq(Attendance::getLessonId, attendanceDTO.getLessonId())
			    .eq(Attendance::getStudentId, attendanceDTO.getStudentId())
				.set(Attendance::getStatus, attendanceDTO.getStatus());
	
		Boolean res = this.update(wrapper);
		
		if (!res) { 
			Attendance attendance = new Attendance();
			BeanUtils.copyProperties(attendanceDTO, attendance);
			this.save(attendance);
		}
	}
	
	@Override
	@Transactional
	public void verifyAndSaveAttendance(AttendanceVerifyDTO verifyDTO) {
		Rollcall rollcall = rollcallService.getById(verifyDTO.getLessonId());
		if (rollcall == null) {
	        throw new BusinessException(404, "找不到該堂課或此課程尚未開啟點名");
	    }
		if(rollcall.getCode().equals(verifyDTO.getCode())) {
			AttendanceDTO attendanceDTO = new AttendanceDTO();
			BeanUtils.copyProperties(verifyDTO, attendanceDTO);
			saveOrUpdateAttendance(attendanceDTO);
		}else {
			throw new BusinessException(400, "點名碼錯誤");
		}
	}
	

//	@Override
//	public Boolean buildLessonAttendaces(Lesson lesson) {
//		List<Attendance> attendanceList = enrollmentService.getStudentListByCourseId(lesson.getCourseId()).stream()
//				.map(student -> {
//					Attendance attendance = new Attendance();
//					attendance.setLessonId(lesson.getId());
//					attendance.setStudentId(student.getId());
//					return attendance;
//				}).collect(Collectors.toList());
//
//		return this.saveBatch(attendanceList);
//	}
//
//	@Override
//	public Boolean buildLessonAttendaces(List<Lesson> lessonList) {
//		for (Lesson lesson : lessonList) {
//			this.buildLessonAttendaces(lesson);
//		}
//		return true;
//	}
	@Override
	public List<StudentAttendanceStatsVO> getStudentAttendanceStats(Long courseId) {
		return baseMapper.selectStatsByStudent(courseId);
	}

	@Override
	public List<LessonAttendanceStatsVO> getLessonAttendanceStats(Long courseId) {
		return baseMapper.selectStatsByLesson(courseId);
	}

	

}
