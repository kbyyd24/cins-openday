package cn.edu.swpu.cins.openday.service;

import javax.mail.MessagingException;

public interface MailService {
	void send(String to, String subject, String text) throws MessagingException;
}
