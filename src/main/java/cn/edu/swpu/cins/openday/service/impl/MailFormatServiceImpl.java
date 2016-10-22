package cn.edu.swpu.cins.openday.service.impl;

import cn.edu.swpu.cins.openday.service.MailFormatService;
import cn.edu.swpu.cins.openday.util.URLCoderUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailFormatServiceImpl implements MailFormatService {

	@Value("${openday.server.host}")
	private String host;
	@Value("${openday.server.port}")
	private int port;
	@Value("${openday.path.user.enable}")
	private String enablePath;

	@Override
	public String getSignUpSubject(String username) {
		final String format = "尊敬的username，欢迎注册CINS开放日活动";
		return format.replaceAll("username", username);
	}

	@Override
	public String getSignUpContent(String mail, String token) {
		final String pervious = "请访问下面的链接以激活您的邮箱（<b>30分钟有效</b>）<br/>";
		String baseMail = URLCoderUtil.encode(mail);
		String baseToken = URLCoderUtil.encode(token);
		String link = "http://"+ host + ':' + port + enablePath + '/' +	baseMail + "/" + baseToken;
		String htmlLink = "<a href='" + link + "' target='_blank'>" + link + "</a>";
		return pervious + htmlLink;
	}
}
