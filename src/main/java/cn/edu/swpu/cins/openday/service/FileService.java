package cn.edu.swpu.cins.openday.service;

import cn.edu.swpu.cins.openday.enums.service.MatchServiceResultEnum;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileService {
	MatchServiceResultEnum saveAnswer(MultipartFile multipartFile, int registId);
}
