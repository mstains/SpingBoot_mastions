package com.mstains.team;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mstains.team.dao")
public class MastainsApplication {

	public static void main(String[] args) {

		SpringApplication.run(MastainsApplication.class, args);
	}

}
