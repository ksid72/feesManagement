package com.fees;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.fees.entity"})
public class FeesManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeesManagementApplication.class, args);
	}

}
