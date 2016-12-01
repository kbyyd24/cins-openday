package cn.edu.swpu.cins.openday.end.advice.service.impl;

import cn.edu.swpu.cins.openday.end.advice.service.MailFormatService;
import org.springframework.stereotype.Service;

@Service
public class FailedMailFormatService implements MailFormatService{
	@Override
	public String getSubject() {
		return "数智汇通杯大数据竞赛结束通知";
	}

	@Override
	public String getContent() {
		return "<p><strong>非常遗憾你没能进入\"数智汇通杯\"大数据竞赛前十名</strong>,但你仍然可以以观众身份参加明晚的答辩环节,安排如下:</p>\n" +
			"\n" +
			"<ul><li>时间: 2016年12月2日 19:30</li>\n" +
			"<li>地点: 明理楼 B306</li>\n" +
			"</ul>";
	}
}
