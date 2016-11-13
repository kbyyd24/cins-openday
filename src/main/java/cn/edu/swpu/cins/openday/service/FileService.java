package cn.edu.swpu.cins.openday.service;

import cn.edu.swpu.cins.openday.enums.service.MatchServiceResultEnum;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileService {
	MatchServiceResultEnum saveFile(MultipartFile multipartFile, int registId);
}
