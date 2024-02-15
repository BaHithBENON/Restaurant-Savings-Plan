package com.rewardomain.regitry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaRegitryNamingServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaRegitryNamingServerApplication.class, args);
	}
}
