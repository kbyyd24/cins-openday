package cn.edu.swpu.cins.openday.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncodeUtil {

	private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

	public static String encode(String password) {
		return encoder.encode(password);
	}
}
