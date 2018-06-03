package cn.chouchou.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.chouchou.App;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes={App.class})
public class MailTest {
     
	@Autowired
	private  JavaMailSender   javaMailSender;
	
	
	@Test
	public void sendSimplemail(){
		SimpleMailMessage   message=new SimpleMailMessage();
		message.setFrom("344630476@qq.com");
		message.setTo("2642862135@qq.com");
		message.setSubject("这是邮件主题！！");
		message.setText("这是邮件内容");
		javaMailSender.send(message);
	}
}
