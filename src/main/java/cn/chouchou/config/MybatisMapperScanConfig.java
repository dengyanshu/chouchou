package cn.chouchou.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter({MybatisSessionFactoryBeanConfig.class})
public class MybatisMapperScanConfig {
    
	@Bean
	public  MapperScannerConfigurer mapperScannerConfigurer(){
		  MapperScannerConfigurer  scanner=new MapperScannerConfigurer();
		  scanner.setBasePackage("cn.chouchou.dao");
		  scanner.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
		
		return scanner;
	}
	
}
