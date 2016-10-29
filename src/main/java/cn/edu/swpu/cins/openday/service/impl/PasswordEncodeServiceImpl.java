package cn.edu.swpu.cins.openday.service.impl;

import cn.edu.swpu.cins.openday.service.PasswordEncoderService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncodeServiceImpl implements PasswordEncoderService {

	private final PasswordEncoder encoder = new BCryptPasswordEncoder();

	@Override
	public String encode(String password) {
		return encoder.encode(password);
	}

	@Override
	public boolean match(String password, String encodedPassword) {
		return encoder.matches(password, encodedPassword);
	}
}
