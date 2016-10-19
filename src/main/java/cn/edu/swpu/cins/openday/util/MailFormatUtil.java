package cn.edu.swpu.cins.openday.util;

import java.util.Base64;

public class MailFormatUtil {

	public static String getSignUpSubject (String username) {
		final String format = "尊敬的username，欢迎注册CINS开放日活动";
		return format.replaceAll("username", username);
	}

	public static String getSignUpContent (String mail, String token) {
		final String pervious = "请访问下面的链接以激活您的邮箱（<b>30分钟有效</b>）<br/>";
		Base64.Encoder encoder = Base64.getUrlEncoder();
		String baseMail = encoder.encodeToString(mail.getBytes());
		String baseToken = encoder.encodeToString(token.getBytes());
		String link = "http://host/user/enable/" +
						baseMail +
						"/" +
						baseToken;
		String htmlLink = "<a href='" + link + "' target='_blank'>" + link + "</a>";
		return pervious + htmlLink;
	}
}
