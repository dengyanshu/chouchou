package cn.chouchou.config;

import java.io.IOException;
import java.sql.SQLSyntaxErrorException;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
public class MybatisSessionFactoryBeanConfig {
	
	@Autowired
	private  ComboPooledDataSource  dataSource;
        
	@Bean
	public   SqlSessionFactoryBean   sqlSessionFactoryBean(){
		SqlSessionFactoryBean  sessionFactory=new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		
		ResourcePatternResolver  resource_resolver=new PathMatchingResourcePatternResolver();
		Resource batisConfig=resource_resolver.getResource("classpath:mybatis.xml");
		Resource[] mapper_location=null;
		try {
			mapper_location = resource_resolver.getResources("classpath:cn.chouchou.dao.*");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sessionFactory.setConfigLocation(batisConfig);
		sessionFactory.setMapperLocations(mapper_location);
		return sessionFactory;
	}
}
