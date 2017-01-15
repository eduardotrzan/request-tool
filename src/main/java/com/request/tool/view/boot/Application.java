package com.request.tool.view.boot;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.request.tool.model.service.interfaces.EmailService;
import com.request.tool.model.service.interfaces.GitService;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.request.tool.model", "com.request.tool.controller"})
@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private GitService gitService;
	
	@Autowired
	private EmailService emailService;
	
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
    
    public static void main(String[] args) throws Exception {
    		Map<String, String> attributes = new HashMap<String, String>();
    		attributes.put("abc", null);
    		System.out.println("abc");
    	
	}

    @Override
    public void run(String... strings) throws Exception {
    	LOGGER.info("Running change label test.");
    }
}
