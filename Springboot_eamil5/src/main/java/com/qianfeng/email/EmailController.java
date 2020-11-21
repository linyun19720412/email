package com.qianfeng.email;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EmailController {
	@Autowired
	private EmailService emailService;
	@RequestMapping("/simple")
	@ResponseBody
	public String sendSimpleEmail() {
		emailService.sendSimpleMail("cpzly@126.com", "你好", "明天去钓鱼");
		return "success!";
	}
	@RequestMapping("/attach")
	@ResponseBody
	public String sendAttachmentEmail() {
		File file=new File("src/main/resources/static/kafka.docx"); 
		emailService.sendAttachMail("cpzly@126.com", "有附件", "你好呀", file);
		return "成功发送附件！";
	}
	
	@RequestMapping("/template")
	@ResponseBody
	public String sendTemplateEmail() {
		emailService.sendTemplateMail("cpzly@126.com", "模板邮件", "info.html");
		return "成功发送！";
	}
}
