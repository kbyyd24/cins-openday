package cn.edu.swpu.cins.openday.service.impl;

import cn.edu.swpu.cins.openday.service.TimeService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TimeServiceImpl implements TimeService {
	@Override
	public long getCurrentTimeMillis() {
		return System.currentTimeMillis();
	}

	@Override
	public String getDate() {
		return new SimpleDateFormat("yyyy-MM-dd")
			.format(new Date(
				this.getCurrentTimeMillis()));
	}
}
