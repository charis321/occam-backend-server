package com.charis.occam_spm_sys.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.charis.occam_spm_sys.entity.Message;
import com.charis.occam_spm_sys.model.dto.MessageDTO;
import com.charis.occam_spm_sys.model.vo.MessageVO;

public interface MessageService extends IService<Message>{
	
	public List<MessageVO> getMessagesBySenderId(Long senderId);
	public List<MessageVO> getMessagesByReceiverId(Long receiverId);
	
	public void addMessage(MessageDTO messageDTO);
}
