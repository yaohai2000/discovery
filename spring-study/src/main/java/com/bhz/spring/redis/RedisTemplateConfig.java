package com.bhz.spring.redis;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.bhz.spring.ex3.Product;

@Configuration
public class RedisTemplateConfig {
	
	@Bean(name="redisCF")
	public RedisConnectionFactory redisCF(){
		JedisConnectionFactory jcf = new JedisConnectionFactory();
		jcf.setHostName("127.0.0.1");
		jcf.setPort(6379);
		return jcf;
	}
	
	@Bean(name="redis")
	public RedisTemplate<String,Product> redis(@Qualifier("redisCF")RedisConnectionFactory rcf){
		RedisTemplate<String,Product> redis = new RedisTemplate<String,Product>();
		redis.setConnectionFactory(rcf);
		return redis;
	}
}
