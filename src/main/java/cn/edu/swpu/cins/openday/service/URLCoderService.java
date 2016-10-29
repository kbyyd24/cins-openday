package cn.edu.swpu.cins.openday.service;

public interface URLCoderService {
	String encode(String token);

	String decode(String baseToken);
}
