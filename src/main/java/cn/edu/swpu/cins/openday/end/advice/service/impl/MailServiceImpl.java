package cn.edu.swpu.cins.openday.end.advice.service.impl;

import cn.edu.swpu.cins.openday.end.advice.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

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
	public void sendBatchMailWithCC(String subject, String content, List<String> cc, List<String> to) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
		helper.setSubject(subject);
		helper.setText(content, true);
		helper.setFrom(fromMail);
		helper.setTo(to.stream().toArray(String[]::new));
		helper.setCc(cc.stream().toArray(String[]::new));
		javaMailSender.send(message);
	}
}
