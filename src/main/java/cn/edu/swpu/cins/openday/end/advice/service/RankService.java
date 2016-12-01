package cn.edu.swpu.cins.openday.end.advice.service;

import cn.edu.swpu.cins.openday.end.advice.model.RankMsg;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RankService {

	List<RankMsg> getRankMsg();

}
