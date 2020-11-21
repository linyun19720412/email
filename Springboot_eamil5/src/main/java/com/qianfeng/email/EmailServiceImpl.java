package com.qianfeng.email;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Template;

@Service
public class EmailServiceImpl implements EmailService {
	@Autowired
	private EmailConfig emailConfig;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private FreeMarkerConfigurer freeMakerConfigurer; 
		
	
	@Override
	public void sendSimpleMail(String sendTo, String title, String content) {
		//简单邮件的发送
		SimpleMailMessage message =new SimpleMailMessage();
		message.setFrom(emailConfig.getEmailFrom());
		message.setTo(sendTo);
		message.setSubject(title);
		message.setText(content);
		
		mailSender.send(message);
		
		
	}
	//发送带附件的邮件
	@Override
	public void sendAttachMail(String sendTo, String title, String content, File file) {
		// TODO Auto-generated method stub
		MimeMessage msg =mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper=new MimeMessageHelper(msg, true);
			helper.setFrom(emailConfig.getEmailFrom());
			helper.setTo(sendTo);
			helper.setSubject(title);
			helper.setText(content);
			FileSystemResource r=new FileSystemResource(file);
			helper.addAttachment("kafka.docx", r);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		mailSender.send(msg);
	}
	@Override
	public void sendTemplateMail(String sendTo, String title, String info) {
		// TODO Auto-generated method stub
		MimeMessage msg =mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper=new MimeMessageHelper(msg, true);
			helper.setFrom(emailConfig.getEmailFrom());
			helper.setTo(sendTo);
			helper.setSubject(title);
			//封装模板使用的数据
			Map<String,Object> model=new HashMap<>();
			model.put("username", "xiaoming");
			//得到模板
			Template template=freeMakerConfigurer.getConfiguration().getTemplate(info);
			String html=FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
			helper.setText(html,true);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		mailSender.send(msg);
		
	}

}
