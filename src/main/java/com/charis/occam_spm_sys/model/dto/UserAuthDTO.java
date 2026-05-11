package com.charis.occam_spm_sys.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserAuthDTO {

	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	private String name;
	private Integer role;
	private Integer sex;
	private String avatar;
	private String token;

	public UserAuthDTO(Long id, String name, Integer role, Integer sex, String avatar, String token) {
		this.id = id;
		this.name = name;
		this.role = role;
		this.sex = sex;
		this.avatar = avatar;
		this.token = token;
	}
}
