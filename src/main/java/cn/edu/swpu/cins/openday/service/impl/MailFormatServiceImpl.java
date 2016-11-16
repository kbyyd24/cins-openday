package cn.edu.swpu.cins.openday.service.impl;

import cn.edu.swpu.cins.openday.service.MailFormatService;
import cn.edu.swpu.cins.openday.service.URLCoderService;
import org.springframework.beans.factory.annotation.Autowired;
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
	private URLCoderService urlCoderService;

	@Autowired
	public MailFormatServiceImpl(URLCoderService urlCoderService) {
		this.urlCoderService = urlCoderService;
	}

	@Override
	public String getSignUpSubject(String username) {
		final String format = "尊敬的username，欢迎注册CINS开放日活动";
		return format.replaceAll("username", username);
	}

	@Override
	public String getSignUpContent(String mail, String token) {
		final String previous = "请访问下面的链接以激活您的邮箱（<b>30分钟有效</b>）<br/>";
		String baseMail = urlCoderService.encode(mail);
		String baseToken = urlCoderService.encode(token);
		String link = "http://"+ host + ':' + port + "/" + enablePath + "?mail=" +	baseMail + "&token=" + baseToken;
		String htmlLink = "<a href='" + link + "' target='_blank'>" + link + "</a>";
		return previous + htmlLink;
	}
}
