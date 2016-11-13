package cn.edu.swpu.cins.openday.service;

import cn.edu.swpu.cins.openday.enums.service.MatchServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.*;
import cn.edu.swpu.cins.openday.model.persistence.Match;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MatchService {
	MatchServiceResultEnum addMatch(UpMatch upMatch);

	List<Match> getMatches(int page);

	Match getMatch();

	MatchServiceResultEnum joinMatch(MatchRegister matchRegister);

	Match getDataSet(int id);

	RankResult getRankList();

	int getRegistId(int matchId, int userId);

	TeamMsg getTeamMsg(int matchId, int userId);

}
