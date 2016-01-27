package com.bhz.spring.ex3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan
public class SaleServiceConfig {
	@Bean
	public Discount discount(){
		return new Discount();
	}
}
