package com.tietoevry.surest.member.management;

import com.tietoevry.surest.member.management.exception.ErrorMessagesConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@Slf4j
@SpringBootApplication
@EnableConfigurationProperties(ErrorMessagesConfig.class)
public class SurestMemberManagementApplication {

	public static void main(String[] args) {

        SpringApplication.run(SurestMemberManagementApplication.class, args);
	}

}
