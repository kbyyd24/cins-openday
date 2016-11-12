package cn.edu.swpu.cins.openday.service.impl;

import cn.edu.swpu.cins.openday.service.TokenService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {

	@Override
	public String createToken() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}
