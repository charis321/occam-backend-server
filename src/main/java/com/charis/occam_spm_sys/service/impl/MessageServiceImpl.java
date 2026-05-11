package com.charis.occam_spm_sys.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.charis.occam_spm_sys.entity.Message;
import com.charis.occam_spm_sys.entity.User;
import com.charis.occam_spm_sys.exception.BusinessException;
import com.charis.occam_spm_sys.mapper.MessageMapper;
import com.charis.occam_spm_sys.model.dto.MessageDTO;
import com.charis.occam_spm_sys.model.vo.MessageVO;
import com.charis.occam_spm_sys.service.MessageService;
import com.charis.occam_spm_sys.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

	@Autowired
	private UserService userService;

	@Override
	@Transactional(readOnly = true)
	public List<MessageVO> getMessagesBySenderId(Long senderId) {
		User sender = userService.getById(senderId);
		if (sender == null)
			throw new BusinessException(400, "寄信人不存在，請確認資料正確無誤");

		return baseMapper.selectBySenderId(senderId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<MessageVO> getMessagesByReceiverId(Long receiverId) {
		User receiver = userService.getById(receiverId);
		if (receiver == null)
			throw new BusinessException(400, "收信人不存在，請確認資料正確無誤");

		return baseMapper.selectByReceiverId(receiverId);
	}

	@Override
	@Transactional
	public void addMessage(MessageDTO messageDTO) {
		log.debug("新增訊息 | 寄信人ID: {}，收信人ID: {}", messageDTO.getSenderId(), messageDTO.getReceiverId());
		User sender = userService.getById(messageDTO.getSenderId());
		User receiver = userService.getById(messageDTO.getReceiverId());
		if (sender == null || receiver == null) {
			log.warn("新增訊息失敗: 收信人或寄信人不存在 | 寄信人ID: {}，收信人ID: {}", messageDTO.getSenderId(), messageDTO.getReceiverId());
			throw new BusinessException(400, "收信人或寄信人不存在，請確認資料正確無誤");
		}
		if (sender.getStatus() == 1) {
			log.warn("新增訊息失敗: 寄信人帳號已經被停用 | 寄信人ID: {}，收信人ID: {}", messageDTO.getSenderId(),messageDTO.getReceiverId());
			throw new BusinessException(403, "此寄信人帳號已經被停用");
		}
		
		Message message = new Message();
		BeanUtils.copyProperties(messageDTO, message);
		this.save(message);
	}

}
