package cn.edu.swpu.cins.openday.service;

public interface PasswordEncoderService {
	String encode(String password);

	boolean match(String password, String encodedPassword);
}
