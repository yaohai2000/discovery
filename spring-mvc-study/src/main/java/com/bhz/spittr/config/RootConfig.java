package com.bhz.spittr.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
//@ComponentScan(basePackages={"com.bhz"},excludeFilters={@Filter(type=FilterType.ANNOTATION,value=EnableWebMvc.class)})
@ComponentScan(basePackages={"com.bhz"})
public class RootConfig {
	
}
