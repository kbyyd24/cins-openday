package cn.edu.swpu.cins.openday.service;

public interface MailFormatService {
	String getSignUpSubject(String username);

	String getSignUpContent(String mail, String token);
}
