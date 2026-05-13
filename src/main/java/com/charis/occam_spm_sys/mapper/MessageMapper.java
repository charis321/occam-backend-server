package com.charis.occam_spm_sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.charis.occam_spm_sys.entity.Message;
import com.charis.occam_spm_sys.model.vo.MessageVO;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {

	@Select("""
			select m.id as id,
					m.sender_id,
					su.name as sender_name,
					su.role as sender_role,
					su.no as sender_no,
					su.email as sender_email,
					m.receiver_id,
					ru.name as receiver_name,
					ru.role as receiver_role,
					ru.no as receiver_no,
					ru.email as receiver_email,
					m.title,
					m.body,
					m.attachment,
					m.create_time
			from "messages" m
			left join "users" su
			on su.id = m.sender_id
			left join "users" ru
			on ru.id = m.receiver_id
			where sender_id = #{senderId}
			order by m.create_time desc
			""")
	public List<MessageVO> selectBySenderId(Long senderId);
	
	@Select("""
			select m.id as id,
					m.sender_id,
					su.name as sender_name,
					su.role as sender_role,
					su.no as sender_no,
					su.email as sender_email,
					m.receiver_id,
					ru.name as receiver_name,
					ru.role as receiver_role,
					ru.no as receiver_no,
					ru.email as receiver_email,
					m.title,
					m.body,
					m.attachment,
					m.create_time
			from "messages" m
			left join "users" su
			on su.id = m.sender_id
			left join "users" ru
			on ru.id = m.receiver_id
			where receiver_id = #{receiverId}
			order by m.create_time desc
			""")
	public List<MessageVO> selectByReceiverId(Long receiverId);
}
