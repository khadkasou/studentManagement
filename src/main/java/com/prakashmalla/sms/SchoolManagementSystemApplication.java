package com.prakashmalla.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import com.prakashmalla.sms.config.AdminProperties;


@SpringBootApplication
@ComponentScan(basePackages ={"com.prakashmalla"})
@EnableConfigurationProperties(AdminProperties.class)
public class SchoolManagementSystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(SchoolManagementSystemApplication.class, args);
	}

}
