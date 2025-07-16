package com.charis.occam_spm_sys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.charis.occam_spm_sys.mapper")
public class OccamSpmSysApplication {

	public static void main(String[] args) {
		SpringApplication.run(OccamSpmSysApplication.class, args);
	}

}
