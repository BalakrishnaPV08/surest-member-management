package com.tietoevry.surest.member.management;

<<<<<<< HEAD
import com.tietoevry.surest.member.management.exception.ErrorMessagesConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

=======
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@Slf4j
>>>>>>> 602906156e831d4c39cb12030336b68fdf676fc6
@SpringBootApplication
@EnableConfigurationProperties(ErrorMessagesConfig.class)
public class SurestMemberManagementApplication {

	public static void main(String[] args) {

        SpringApplication.run(SurestMemberManagementApplication.class, args);
	}

}
