package com.charis.occam_spm_sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.charis.occam_spm_sys.common.Result;
import com.charis.occam_spm_sys.model.dto.MessageDTO;
import com.charis.occam_spm_sys.model.vo.MessageVO;
import com.charis.occam_spm_sys.service.MessageService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/message")
public class MessageController {
	
	@Autowired
	private MessageService messageService;
	
	@GetMapping("/sender/{senderId}")
	public Result getMessagesBySender(@PathVariable Long senderId) {
		log.debug("嘗試獲取訊息，以寄信人為條件 | 寄信人ID:", senderId);
		List<MessageVO>  voList = messageService.getMessagesBySenderId(senderId);
		return Result.success("獲取訊息成功", voList);
	}
	
	@GetMapping("/receiver/{receiverId}")
	public Result getMessagesByReceiver(@PathVariable Long receiverId) {
		log.debug("嘗試獲取訊息，以收信人為條件 | 收信人ID:", receiverId);
		List<MessageVO>  voList = messageService.getMessagesByReceiverId( receiverId);
		return Result.success("獲取訊息成功", voList);
	}
	
	@PostMapping
	public Result addMessage(@Valid @RequestBody MessageDTO messageDTO) {
		log.debug("嘗試新增訊息 | 寄信人ID: {}，收信人ID: {}", messageDTO.getSenderId(),messageDTO.getReceiverId());
		messageService.addMessage(messageDTO);
		return Result.success("新增訊息成功");
	}
	
}
