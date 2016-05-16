package com.bhz.spring.ex2;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class CDPlayerConfig {
	
	@Bean
	@Qualifier("sgtPepper")
	public CompactDisc sgtPeppers(){
		return new SgtPeppers();
	}
	
	@Bean
	@Qualifier("purpose")
	public CompactDisc purpose(){
		return new Purpose();
	}
	
	@Bean
	public CDPlayer cdPlayer(@Qualifier("sgtPepper")CompactDisc cd){
		return new CDPlayer(cd);
	}
}
