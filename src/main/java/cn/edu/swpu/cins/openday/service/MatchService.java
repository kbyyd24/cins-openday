package cn.edu.swpu.cins.openday.service;

import cn.edu.swpu.cins.openday.enums.service.MatchServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.UpMatch;
import org.springframework.stereotype.Service;

@Service
public interface MatchService {
	MatchServiceResultEnum addMatch(UpMatch upMatch);
}
