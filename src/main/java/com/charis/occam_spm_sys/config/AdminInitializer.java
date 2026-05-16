package com.charis.occam_spm_sys.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.charis.occam_spm_sys.common.OCPasswordEncoder;
import com.charis.occam_spm_sys.entity.User;
import com.charis.occam_spm_sys.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AdminInitializer implements CommandLineRunner {

	@Autowired
	private UserService userService;
	@Autowired
	private OCPasswordEncoder ocPasswordEncoder;

	private final String ADMIN_DEFAULT_EMAIL = "test@gmail.com";
	private final String ADMIN_DEFAULT_PASSWORD = "betelgeuse";

	@Override
	public void run(String... args) throws Exception {
		try {
			User existingUser = userService.findUserByEmail(ADMIN_DEFAULT_EMAIL);
			if (existingUser == null) {
				User adminUser = new User();
				adminUser.setName("admin");
				adminUser.setEmail(ADMIN_DEFAULT_EMAIL);
				adminUser.setPassword(ocPasswordEncoder.encode(ADMIN_DEFAULT_PASSWORD));
				adminUser.setRole(2);

				boolean saved = userService.save(adminUser);
				if (!saved) {
					log.error("初始化管理員用戶寫入資料庫失敗");
				}
				log.info("初始化管理員用戶成功");
			} else {
				log.info("已有管理員用戶，略過初始化");
			}
		} catch (Exception e) {
			log.error("初始化發生異常", e);
		}
	}
}
