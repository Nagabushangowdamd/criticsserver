package com.nk.critics;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CriticsApplication {

	@Autowired
	static JSONObject getJsonBean;
	
	public static void main(String[] args) {
		SpringApplication.run(CriticsApplication.class, args);
		System.out.println("We welcome you to our Critics App");
	}

}
