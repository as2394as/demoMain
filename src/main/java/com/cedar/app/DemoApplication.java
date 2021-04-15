package com.cedar.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DemoApplication{

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public SpringApplicationContext getContext()
	{
		return new SpringApplicationContext();
	}
	
	@Bean
	public RestTemplate restTemplate() {
	 
	    SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
	    factory.setConnectTimeout(3000);
	    factory.setReadTimeout(3000);
	    return new RestTemplate(factory);
	}
}

