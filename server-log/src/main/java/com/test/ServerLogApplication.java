package com.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.test.business.ServerInfoService;

@SpringBootApplication
public class ServerLogApplication implements CommandLineRunner  {
	

	private static final Logger LOGGER = LoggerFactory.getLogger(ServerLogApplication.class);

	@Autowired
	ServerInfoService serverInfoService;	

	public static void main(String[] args) {
		SpringApplication.run(ServerLogApplication.class, args);
	}	
	
	@Override
	public void run(String... args) throws Exception {
		
		//serverInfoService.LoadFile(args[1]);
	}
	

}