package com.charis.occam_spm_sys.entity;

import java.time.OffsetDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("rollcalls")
public class Rollcall {
	
	@TableId(type = IdType.INPUT)
	private Integer lessonId;
    private int mode;
  
    private Integer rotationTime;
    private OffsetDateTime nextRotationTime; 
    private OffsetDateTime startTime; 
    private OffsetDateTime endTime; 
   
    private int status;
    private String code;
}
