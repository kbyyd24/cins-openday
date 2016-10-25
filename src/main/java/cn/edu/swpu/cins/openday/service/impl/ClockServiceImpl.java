package cn.edu.swpu.cins.openday.service.impl;

import cn.edu.swpu.cins.openday.service.ClockService;
import org.springframework.stereotype.Service;

@Service
public class ClockServiceImpl implements ClockService {
	@Override
	public long getCurrentTimeMillis() {
		return System.currentTimeMillis();
	}
}
