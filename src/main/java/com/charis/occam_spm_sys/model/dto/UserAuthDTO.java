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
    private String token;

    public UserAuthDTO(Long id, String name, Integer role, String token) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.token = token;
    }
}
