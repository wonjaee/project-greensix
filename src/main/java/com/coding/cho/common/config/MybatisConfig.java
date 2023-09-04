package com.coding.cho.common.config;

import javax.sql.DataSource;

import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class MybatisConfig {//mybatisConfig
	
	//JPA 사용하기때문에 자동구성됩니다.
	private final DataSource dataSource;
	
	private final ApplicationContext ac;
	
	@Bean
	@ConfigurationProperties(prefix = "mybatis.configuration")
	org.apache.ibatis.session.Configuration mybatisConfiguration() {
		org.apache.ibatis.session.Configuration config= 
				new org.apache.ibatis.session.Configuration();
		config.setLogImpl(StdOutImpl.class);//쿼리확인하기위한 설정
		return config;
	}
	
	//1.SqlSessionFactiory
	@Bean
	SqlSessionFactory sqlSessionFactiory() throws Exception {
		SqlSessionFactoryBean sfb=new SqlSessionFactoryBean();
		sfb.setDataSource(dataSource);
		
		String locationPattern="classpath:static/mapper/**/*-mapper.xml";
		Resource[] mapperLocations=ac.getResources(locationPattern);
		//Resource[] mapperLocations=new PathMatchingResourcePatternResolver().getResources(locationPattern);
		sfb.setMapperLocations(mapperLocations);
		
		sfb.setConfiguration(mybatisConfiguration());
		
		return sfb.getObject();
	}
	
	@Bean
	SqlSessionTemplate sqlSessionTemplate() throws Exception{
		return new SqlSessionTemplate(sqlSessionFactiory());
	}

	

}
