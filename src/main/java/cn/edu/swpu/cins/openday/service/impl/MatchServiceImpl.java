package cn.edu.swpu.cins.openday.service.impl;

import cn.edu.swpu.cins.openday.dao.persistence.MatchDao;
import cn.edu.swpu.cins.openday.enums.service.MatchServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.MatchRegistor;
import cn.edu.swpu.cins.openday.model.http.UpMatch;
import cn.edu.swpu.cins.openday.model.persistence.Match;
import cn.edu.swpu.cins.openday.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchServiceImpl implements MatchService {
	private MatchDao matchDao;
	private final int offset = 4;

	@Autowired
	public MatchServiceImpl(MatchDao matchDao) {
		this.matchDao = matchDao;
	}

	@Override
	@Transactional(rollbackFor = DataAccessException.class)
	public MatchServiceResultEnum addMatch(UpMatch upMatch) {
		int line = matchDao.addMatch(upMatch);
		if (line == 1) {
			return MatchServiceResultEnum.ADD_SUCCESS;
		}
		return MatchServiceResultEnum.ADD_FAILED;
	}

	@Override
	public List<Match> getMatches(int page) {
		int limit = (page - 1) * offset;
		List<Match> matches = matchDao.getMatches(limit, offset);
		return matches == null ? new ArrayList<>() : matches;
	}

	@Override
	public Match joinMatch(MatchRegistor matchRegistor) {
		return null;
	}
}
