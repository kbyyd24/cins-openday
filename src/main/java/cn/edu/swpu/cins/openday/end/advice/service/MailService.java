package cn.edu.swpu.cins.openday.end.advice.service;

import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;

@Service
public interface MailService {

	void sendBatchMailWithCC(String subject, String content, List<String> cc, List<String> to) throws MessagingException;

}
