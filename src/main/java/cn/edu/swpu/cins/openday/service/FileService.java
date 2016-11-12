package cn.edu.swpu.cins.openday.service;

import cn.edu.swpu.cins.openday.enums.service.MatchServiceResultEnum;
import org.springframework.stereotype.Service;

import javax.servlet.http.Part;

@Service
public interface FileService {
	MatchServiceResultEnum saveAnswer(Part part, int registId);
}
