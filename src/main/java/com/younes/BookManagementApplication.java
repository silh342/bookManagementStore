package com.younes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//exclude = {SecurityAutoConfiguration .class}
//(scanBasePackages = {"com.younes.reskilingproject.bookManagement", "com.younes.reskillingproject.userManagement.security"})
@SpringBootApplication
public class BookManagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(BookManagementApplication.class, args);
	}
}
