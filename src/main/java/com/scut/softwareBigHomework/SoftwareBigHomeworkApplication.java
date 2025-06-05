package com.scut.softwareBigHomework;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@MapperScan("com.scut.softwareBigHomework.mapper")
public class SoftwareBigHomeworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoftwareBigHomeworkApplication.class, args);
	}

}
