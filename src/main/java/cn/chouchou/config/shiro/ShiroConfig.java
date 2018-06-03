package cn.chouchou.config.shiro;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.chouchou.shiro.MyFormAuthen;
import cn.chouchou.shiro.MyRealm;


//@Configuration
public class ShiroConfig {
	
	
	/**
	 * 1、web.xml  过滤器Filter ===>shiroFilter
	 * 2、核心shiroFilter 配置
	 *     登陆页
	 *     权限拒绝页
	 *     核心安全管理器
	 *     自定义表单认证filter
	 *     
	 *     过滤链
	 * 
	 * 
	 * 3、核心securityManager 配置
	 *     注入 缓存管理（这里用ehcache）
	 *     注入 shiro的session管理
	 *     注入  核心realm
	 *     注入
	 *     
	 * 4、核心自定义realm(添加用户的其他配置 如md5 sha1 散列算法 salt值等)
	 *    自定义名
	 *    认证
	 *    授权
	 *    缓存清理
	 *     
	 * 注意:shiro的授权的注解 需要开启配置
	 * */
	
	//配置自定义的realm
    @Bean(name="myRealm")
    public MyRealm authRealm(@Qualifier("credentialsMatcher") CredentialsMatcher matcher) {
        MyRealm authRealm=new MyRealm();
        authRealm.setCredentialsMatcher(matcher);
        return authRealm;
    }
    //配置自定义的密码比较器
    @Bean(name="credentialsMatcher")
    public CredentialsMatcher credentialsMatcher() {
    	HashedCredentialsMatcher  hash_credentialsMathcher=new HashedCredentialsMatcher();
    	hash_credentialsMathcher.setHashAlgorithmName("MD5");
    	hash_credentialsMathcher.setHashIterations(1);//salt 散列一次
        return hash_credentialsMathcher;
    }
    
    
    
    
    
    
   //配置核心安全事务管理器
    @Bean(name="securityManager")
    public DefaultWebSecurityManager securityManager(@Qualifier("myRealm") MyRealm myRealm
    		,@Qualifier("ehCacheManager") EhCacheManager ehcacheManager
    		,@Qualifier("sessionManager") DefaultWebSessionManager ssm
    		) {
        System.err.println("--------------shiro已经加载----------------");
        DefaultWebSecurityManager manager=new DefaultWebSecurityManager();
        manager.setRealm(myRealm);
        manager.setCacheManager(ehcacheManager);
        manager.setSessionManager(ssm);
        //还需要一个remember管理
        return manager;
    }
    //注入到安全管理中  缓存管理 shiro 默认是login不缓存 权限才缓存
    @Bean(name="ehCacheManager")
    public EhCacheManager ehCacheManager() {  
        EhCacheManager em = new EhCacheManager();  
        em.setCacheManagerConfigFile("classpath:shiro-ehcache.xml");  
        return em;  
    } 
    //shiro去管理session
    @Bean(name="sessionManager")
    public DefaultWebSessionManager   sessionManager(){
    	DefaultWebSessionManager sessionManager=new DefaultWebSessionManager();
    	sessionManager.setDeleteInvalidSessions(true);
    	sessionManager.setGlobalSessionTimeout(600000);
    	return  sessionManager;
    }
    //还有一个remember
    
    
    
    
    
	@Bean(name="shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") DefaultWebSecurityManager manager
    		,@Qualifier("myFormAuthFilter") FormAuthenticationFilter  filter
    		) {
        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
        bean.setSecurityManager(manager);
        //配置登录的url和登录成功的url
        bean.setLoginUrl("/login.html");//可以不配置 shiro会自动找根目录下的login.jsp
        bean.setSuccessUrl("/home.jsp");
        bean.setUnauthorizedUrl("/refuse.jsp");//未授权的
        
        
        //注入自定义的表单认证过滤器  来解决前台验证码等的判断
        Map<String,Filter> filterMap=new HashMap<String,Filter>();
        filterMap.put("authc", filter);
        bean.setFilters(filterMap);
        
        //配置访问权限
        LinkedHashMap<String, String> filterChainDefinitionMap=new LinkedHashMap<>();
        filterChainDefinitionMap.put("/image/*", "anon"); //表示可以匿名访问
        filterChainDefinitionMap.put("/css/*", "anon"); 
        filterChainDefinitionMap.put("/js/*","anon");
        
        //登出的自动处理 可以不存在此action
        filterChainDefinitionMap.put("/logout.action", "logout");
        
        filterChainDefinitionMap.put("/login.html","anon");
        filterChainDefinitionMap.put("/jsp/login","anon");
        
        
        filterChainDefinitionMap.put("/**", "authc");//表示需要认证才可以访问
        
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }
	
	@Bean(name="myFormAuthFilter")
	public  FormAuthenticationFilter  formAuthenticationFilter(){
		FormAuthenticationFilter  formah_filter=new MyFormAuthen();
		return  formah_filter;
	}
	
    
    
   
    
    
    //下面配置shiro的类代理注解扫描等
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creator=new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") DefaultWebSecurityManager manager) {
        AuthorizationAttributeSourceAdvisor advisor=new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }
	
}
