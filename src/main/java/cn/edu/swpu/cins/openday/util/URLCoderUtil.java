package cn.edu.swpu.cins.openday.util;

import java.util.Base64;

public class URLCoderUtil {

	private static final Base64.Encoder encoder = Base64.getUrlEncoder();
	private static final Base64.Decoder decoder = Base64.getUrlDecoder();

	public static String encode(String token) {
		return encoder.encodeToString(token.getBytes());
	}

	public static String decode(String baseToken) {
		return new String(decoder.decode(baseToken));
	}
}
