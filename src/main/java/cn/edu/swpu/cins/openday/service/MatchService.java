package cn.edu.swpu.cins.openday.service;

import cn.edu.swpu.cins.openday.enums.service.MatchServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.MatchRegister;
import cn.edu.swpu.cins.openday.model.http.RankResult;
import cn.edu.swpu.cins.openday.model.http.TeamMsg;
import cn.edu.swpu.cins.openday.model.persistence.Match;
import org.springframework.stereotype.Service;

@Service
public interface MatchService {

	Match getMatch();

	MatchServiceResultEnum joinMatch(MatchRegister matchRegister, int captainId, int matchId);

	Match getDataSet(int id);

	RankResult getRankList();

	int getGroupId(int matchId, int userId);

	TeamMsg getTeamMsg(int matchId, int userId);

}
