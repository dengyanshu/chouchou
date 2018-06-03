package cn.chouchou;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * springbootapplication:
 * a、configuration
 * b、enable aotu config(依赖不仅仅是jar包 而且会初始化到spring 容器中 包括tomcat[private 方便war包发布] 
 *  dispatcherServelt mvc注解 jackson json处理等)
 * c、component
 * 
 * */

@SpringBootApplication
@Controller
@RequestMapping("/app")
@PropertySource({"classpath:jdbc.properties"})
public class App extends  SpringBootServletInitializer {
    
	@RequestMapping("/hello")
	public @ResponseBody String hello(){
		return "hello chouchou!spring boot project!";
	}

	@Value("${jdbc.driverClass}")
    private 	String driverClass;
	@Value("${jdbc.user}")
    private 	String  user;
    @Value("${jdbc.password}")
    private 	String  password;
    @Value("${jdbc.url}")
    private 	String  jdbcUrl;
    
	@Bean
	public  ComboPooledDataSource  dataSource()throws Exception{
		ComboPooledDataSource  ds=new ComboPooledDataSource();
		ds.setDriverClass(driverClass);
		ds.setUser(user);
		ds.setPassword(password);
		ds.setJdbcUrl(jdbcUrl);
		return  ds;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
         SpringApplication.run(App.class, args);
	}
	
    @Override
    protected SpringApplicationBuilder configure(
    		SpringApplicationBuilder builder) {
    	// TODO Auto-generated method stub
    	return builder.sources(App.class);
    }

}
