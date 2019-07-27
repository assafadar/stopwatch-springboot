package com.shiled.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.shiled.task.dao.hibernate.HibernateUtils;

@EnableAutoConfiguration
@SpringBootApplication
public class MainApp {
	
	public static void main(String[] args) {
		SpringApplication.run(MainApp.class, args);
		HibernateUtils.init();
	}

}
