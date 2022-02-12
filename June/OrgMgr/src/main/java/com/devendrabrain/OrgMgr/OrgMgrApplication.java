package com.devendrabrain.OrgMgr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EntityScan
//@EnableDiscoveryClient
public class OrgMgrApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrgMgrApplication.class, args);
	}

}
