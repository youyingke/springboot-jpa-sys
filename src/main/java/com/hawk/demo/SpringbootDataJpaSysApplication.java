package com.hawk.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 如果需要执行定时任务需要加@EnableScheduling注解
 */
@SpringBootApplication
@EnableScheduling
public class SpringbootDataJpaSysApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDataJpaSysApplication.class, args);
	}

}
