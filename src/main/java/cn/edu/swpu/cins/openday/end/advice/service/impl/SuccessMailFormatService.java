package cn.edu.swpu.cins.openday.end.advice.service.impl;

import cn.edu.swpu.cins.openday.end.advice.service.MailFormatService;
import org.springframework.stereotype.Service;

@Service
public class SuccessMailFormatService implements MailFormatService{
	@Override
	public String getSubject() {
		return "数智汇通杯大数据竞赛结束通知";
	}

	@Override
	public String getContent() {
		return "<p><strong>恭喜你进入\"数智汇通杯\"大数据竞赛前十名</strong>,现在我们邀请你参加明晚的答辩环节,安排如下:</p>\n" +
			"\n" +
			"<ul><li>时间: 2016年12月2日 19:30</li>\n" +
			"<li>地点: 明理楼 B306</li>\n" +
			"</ul>\n" +
			"\n" +
			"<p>如果确定参加,请在<strong>明天中午12点(2016年12月2日 12:00)前</strong>将比赛代码发送至<a href=\"mailto:528858834@qq.com\" target=\"_blank\">528858834@qq.com</a> <br>\n" +
			"邮件标题为:\"数智汇通大数据竞赛_队伍名\"</p>";
	}
}
