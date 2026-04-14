package com.charis.occam_spm_sys.model.dto;

import lombok.Data;

@Data
public class UserQueryDTO {
    private Long id;
    private String name;
    private Integer sex;      
    private String no;
    private String email;
    private String school;
    private String department;
    private Integer role;       
    private Integer status;     
}