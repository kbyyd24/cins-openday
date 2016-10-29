package cn.edu.swpu.cins.openday.service.impl;

import cn.edu.swpu.cins.openday.service.ClockService;
import cn.edu.swpu.cins.openday.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {

	private ClockService clockService;

	@Autowired
	public TokenServiceImpl(ClockService clockService) {
		this.clockService = clockService;
	}

	@Override
	public String createToken() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}
