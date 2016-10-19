package cn.edu.swpu.cins.openday.service;

public interface MailService {
	void send(String to, String subject, String text);
}
