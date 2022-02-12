package com.devendrabrain.StudentMgr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.devendrabrain.StudentMgr")
@EnableDiscoveryClient
public class StudentMgrApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentMgrApplication.class, args);
	}

}
