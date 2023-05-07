package com.julianduru.learning.ftp;

import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.SystemPropertyUtils;

import java.io.File;
import java.util.List;

@SpringBootApplication
public class FtpApplication {

	public static void main(String[] args) {
		SpringApplication.run(FtpApplication.class, args);
	}


	@Bean
	ApplicationRunner ftpService() {
		return args -> {
			var serverFactory = new FtpServerFactory();

			var listenerFactory = new ListenerFactory();
			listenerFactory.setPort(2221);
			serverFactory.addListener("default", listenerFactory.createListener());

			var userManagerFactory = new PropertiesUserManagerFactory();
			userManagerFactory.setFile(new File(SystemPropertyUtils.resolvePlaceholders("${HOME}/ftp/users.properties")));
			var userManager = userManagerFactory.createUserManager();
			initUsers(userManager);

			serverFactory.setUserManager(userManager);

			var server = serverFactory.createServer();
			server.start();
		};
	}


	private static void initUsers(UserManager userManager) throws Exception {
		var baseUser = new BaseUser();

		baseUser.setName("admin");
		baseUser.setPassword("password");
		baseUser.setHomeDirectory(SystemPropertyUtils.resolvePlaceholders("${HOME}/ftp/inbound"));
		baseUser.setEnabled(true);
		baseUser.setAuthorities(
			List.of(new WritePermission())
		);

		userManager.save(baseUser);
	}


}
