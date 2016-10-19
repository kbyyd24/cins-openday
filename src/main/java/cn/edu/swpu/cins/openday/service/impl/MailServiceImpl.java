package cn.edu.swpu.cins.openday.service.impl;

import cn.edu.swpu.cins.openday.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

	private JavaMailSender javaMailSender;
	@Value("${spring.mail.username}")
	private String fromMail;

	@Autowired
	public MailServiceImpl(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Override
	public void send(String to, String subject, String text) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
		message.setContent(text, "text/html");
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setFrom(fromMail);
		javaMailSender.send(message);
	}
}
