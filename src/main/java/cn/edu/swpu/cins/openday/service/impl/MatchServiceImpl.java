package cn.edu.swpu.cins.openday.service.impl;

import cn.edu.swpu.cins.openday.dao.persistence.*;
import cn.edu.swpu.cins.openday.enums.service.MatchServiceResultEnum;
import cn.edu.swpu.cins.openday.exception.*;
import cn.edu.swpu.cins.openday.model.http.MatchRegister;
import cn.edu.swpu.cins.openday.model.http.Rank;
import cn.edu.swpu.cins.openday.model.http.RankResult;
import cn.edu.swpu.cins.openday.model.http.UpMatch;
import cn.edu.swpu.cins.openday.model.persistence.Group;
import cn.edu.swpu.cins.openday.model.persistence.Match;
import cn.edu.swpu.cins.openday.model.persistence.Registration;
import cn.edu.swpu.cins.openday.model.persistence.User;
import cn.edu.swpu.cins.openday.model.service.ScoreRank;
import cn.edu.swpu.cins.openday.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

@Service
public class MatchServiceImpl implements MatchService {
	private MatchDao matchDao;
	private UserDao userDao;
	private GroupDao groupDao;
	private RegistrationDao registrationDao;
	private ScoreDao scoreDao;
	private final int offset = 4;

	@Autowired
	public MatchServiceImpl(MatchDao matchDao, UserDao userDao, GroupDao groupDao, RegistrationDao registrationDao, ScoreDao scoreDao) {
		this.matchDao = matchDao;
		this.userDao = userDao;
		this.groupDao = groupDao;
		this.registrationDao = registrationDao;
		this.scoreDao = scoreDao;
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
	public Match getMatch() {
		return matchDao.getMatch();
	}

	@Override
	@Transactional(rollbackFor = {DataAccessException.class, SQLException.class, OpenDayException.class})
	public MatchServiceResultEnum joinMatch(MatchRegister matchRegister) {
		List<User> users = userDao.getIds(matchRegister.getMail1(), matchRegister.getMail2());
		if (users.size() < 2) {
			throw new UserException("cannot search two users");
		}
		users.forEach(user -> {
			if (user.getId() == null)
				throw new NoSuchUserException("no such user: " + user.getMail());
		});
		Group group = new Group(matchRegister.getGroupName(), matchRegister.getMatchId());
		int line = groupDao.addGroup(group);
		if (line != 1) {
			throw new GroupException("add group error");
		}
		Integer groupId = groupDao.getGroupId(group);
		if (groupId == null) {
			throw new GroupException("get group failed");
		}
		Registration registration = new Registration(matchRegister.getMatchId(), users.get(0).getId(), groupId);
		line = registrationDao.addRegistration(registration);
		registration.setUserId(users.get(1).getId());
		line += registrationDao.addRegistration(registration);
		if (line != 2) {
			throw new RegistrationException("join match error");
		}
		return MatchServiceResultEnum.JOIN_SUCCESS;
	}

	@Override
	public Match getDataSet(int id) {
		return matchDao.getDataSet(id);
	}

	@Override
	public RankResult getRankList() {
		List<ScoreRank> all = scoreDao.getAll();
		List<Rank> ranks = filterDuplication(all);
		setRank(ranks);
		RankResult rankResult = new RankResult();
		rankResult.setRankList(ranks);
		return rankResult;
	}

	private List<Rank> filterDuplication(List<ScoreRank> all) {
		HashSet<Integer> groupSet = new HashSet<>();
		List<Rank> ranks = new LinkedList<>();
		all.forEach(scoreRank -> {
			if (!groupSet.add(scoreRank.getId())) {
				ranks.add(new Rank(scoreRank));
			}
		});
		return ranks;
	}

	private void setRank(List<Rank> ranks) {
		final int[] i = {1};
		ranks.forEach(rank -> rank.setRank(i[0]++));
	}
}
