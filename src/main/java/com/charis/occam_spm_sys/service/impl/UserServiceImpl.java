package com.charis.occam_spm_sys.service.impl;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.charis.occam_spm_sys.entity.Student;
import com.charis.occam_spm_sys.entity.User;
import com.charis.occam_spm_sys.mapper.UserMapper;
import com.charis.occam_spm_sys.service.StudentService;
import com.charis.occam_spm_sys.service.UserService;
import com.charis.occam_spm_sys.vo.UserInfoVO;




@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{
	
	@Autowired
	private StudentService studentService;
	
	@Override
	public List<User> findUserByEmail(String email){
		LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>();
		lambdaQueryWrapper.eq(User::getEmail,email);
		List<User> res = this.list(lambdaQueryWrapper);
		return res; 
	}

	@Override
	public UserInfoVO getUserInfoVO(Long userId) {
		UserInfoVO vo = new UserInfoVO();
		User user = this.getById(userId);
		BeanUtils.copyProperties(user, vo);
		
		if(user.getRole()==0) {
			Student studentVaild = studentService.checkStudnetVaildByUserId(userId);
			vo.setIsVaild(studentVaild==null?false:true);
			vo.setStudentInfo(studentVaild);
		}
		
		return vo;
	}
	
	@Override
	public List<UserInfoVO> getUserInfoVOList() {
		List<User> userList = this.list();
		List<UserInfoVO> userInfoVOList = userList.stream()
												    .map(user->{
												    	UserInfoVO vo = getUserInfoVO(user.getId());
										  		        return vo;
												    })
													.collect(Collectors.toList());
		
		return userInfoVOList;
	}
	
	@Override
	public Boolean patch(Long userId, Map<String,Object> params) {
		UpdateWrapper<User> wrapper = new UpdateWrapper<>();
		wrapper.eq("id", userId);
		params.forEach((key, value) -> {
	        if (value != null) {
	        	wrapper.set(key, value);
	        }
	    });
		return this.update(wrapper);
	}
}
