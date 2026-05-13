package com.charis.occam_spm_sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.charis.occam_spm_sys.common.Result;
import com.charis.occam_spm_sys.model.dto.RollcallDTO;
import com.charis.occam_spm_sys.model.dto.RollcallQueryDTO;
import com.charis.occam_spm_sys.model.vo.RollcallDetailVO;
import com.charis.occam_spm_sys.model.vo.RollcallVO;
import com.charis.occam_spm_sys.service.RollcallService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/rollcall")
public class RollcallController {

	@Autowired
	private RollcallService rollcallService;
		
	@GetMapping("/{lessonId}")
	public Result getRollcall(@PathVariable Integer lessonId) {
		log.debug("用戶嘗試獲取點名 | lessonId: {}", lessonId);
		RollcallVO vo = rollcallService.getRollcallByLessonId(lessonId);
		return Result.success("取得點名成功", vo);
	}
	@GetMapping("/student/{studentId}")
	public Result getRollcallByStudent(@PathVariable Long studentId) {
		log.debug("學生用戶嘗試獲取進行中的點名 | studentId: {}", studentId);
		List<RollcallDetailVO> voList = rollcallService.getRollcallingsByStudentId(studentId);
		return Result.success("學生用戶取得進行中的點名成功", voList);
	}
	
	@GetMapping("/teacher/{teacherId}")
	public Result getRollcallByTeacher(@PathVariable Long teacherId) {
		log.debug("教師用戶嘗試獲取進行中的點名 | teacherId: {}", teacherId);
		List<RollcallDetailVO> voList = rollcallService.getRollcallingsByTeacherId(teacherId);
		return Result.success("教師用戶取得進行中的點名成功", voList);
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
	@PatchMapping("/{lessonId}")
	public Result updateRollcall(@Valid @PathVariable Integer lessonId, @RequestBody RollcallDTO rollcallDTO) {
		log.debug("用戶嘗試更新點名 | lessonId: {}， rollcall: {}", lessonId, rollcallDTO);
		rollcallDTO.setLessonId(lessonId);	
		rollcallService.handleRollcall(rollcallDTO);
		return Result.success("更新點名成功");
	}

//	@PostMapping
//	public Result addRollcall(@PathVariable Integer lessonId) {
//		return null;
//	}

}
