package cn.edu.swpu.cins.openday.end.advice.service;

import org.springframework.stereotype.Service;

@Service
public interface MailFormatService {

	String getSubject();

	String getContent();

}
