package cn.edu.swpu.cins.openday.service.impl;

import cn.edu.swpu.cins.openday.service.MailService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MailServiceImplTest {

	@Mock
	private JavaMailSender javaMailSender;

	private String fromMail = "cins@gaoyuexiang.cn";

	private MailService mailService;

	@Before
	public void setUp() throws Exception {
		mailService = new MailServiceImpl(javaMailSender, fromMail);
	}

	@Test
	public void test_send_mail() throws Exception {
		MimeMessage mockMsg = Mockito.mock(MimeMessage.class);
		when(javaMailSender.createMimeMessage()).thenReturn(mockMsg);
		String to = "melo@gaoyuexiang.cn";
		String subject = "subject";
		String text = "text";
		mailService.send(to, subject, text);
		verify(javaMailSender).createMimeMessage();
		verify(mockMsg).setContent(eq(text), eq("text/html"));
		verify(javaMailSender).send(mockMsg);
	}
}