package com.charis.occam_spm_sys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
	
	@Bean
    public OpenAPI OCOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("occam 點名系統 API 文件")
                        .description("用於課程管理、學生點名與課程查詢的後端接口")
                        .version("v1.0.0")
                        .contact(new Contact().name("Charis321").email("z411327@gmail.com")));
    }

}
