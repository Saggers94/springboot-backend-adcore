package com.springboot.adcore.crud;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import com.springboot.adcore.crud.model.Data;
import com.springboot.adcore.crud.repository.DataRepository;


@SpringBootApplication
public class SpringbootBackendForAdcoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBackendForAdcoreApplication.class, args);
	}

}


@Component
class DemoCommandLineRunner implements CommandLineRunner{

	
	
	@Override
	public void run(String... args) throws Exception {	
	}
	
	
	
}