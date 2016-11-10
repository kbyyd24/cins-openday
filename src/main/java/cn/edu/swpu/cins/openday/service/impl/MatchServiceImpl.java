package cn.edu.swpu.cins.openday.service.impl;

import cn.edu.swpu.cins.openday.dao.persistence.GroupDao;
import cn.edu.swpu.cins.openday.dao.persistence.MatchDao;
import cn.edu.swpu.cins.openday.dao.persistence.RegistrationDao;
import cn.edu.swpu.cins.openday.dao.persistence.UserDao;
import cn.edu.swpu.cins.openday.enums.service.MatchServiceResultEnum;
import cn.edu.swpu.cins.openday.exception.GroupException;
import cn.edu.swpu.cins.openday.exception.NoSuchUserException;
import cn.edu.swpu.cins.openday.exception.OpenDayException;
import cn.edu.swpu.cins.openday.exception.RegistrationException;
import cn.edu.swpu.cins.openday.model.http.MatchRegistor;
import cn.edu.swpu.cins.openday.model.http.UpMatch;
import cn.edu.swpu.cins.openday.model.persistence.Group;
import cn.edu.swpu.cins.openday.model.persistence.Match;
import cn.edu.swpu.cins.openday.model.persistence.Registration;
import cn.edu.swpu.cins.openday.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MatchServiceImpl implements MatchService {
	private MatchDao matchDao;
	private UserDao userDao;
	private GroupDao groupDao;
	private RegistrationDao registrationDao;
	private final int offset = 4;

	@Autowired
	public MatchServiceImpl(MatchDao matchDao, UserDao userDao, GroupDao groupDao, RegistrationDao registrationDao) {
		this.matchDao = matchDao;
		this.userDao = userDao;
		this.groupDao = groupDao;
		this.registrationDao = registrationDao;
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
	@Transactional(rollbackFor = {DataAccessException.class, SQLException.class, OpenDayException.class})
	public Match joinMatch(MatchRegistor matchRegistor) {
		Integer userId = userDao.getId(matchRegistor.getMail());
		if (userId == null) {
			throw new NoSuchUserException("no such user: " + matchRegistor.getMail());
		}
		Group group = new Group(matchRegistor.getGroupName(), matchRegistor.getMatchId());
		int line = groupDao.addGroup(group);
		if (line != 1) {
			throw new GroupException("add group error");
		}
		Integer groupId = groupDao.getGroupId(group);
		if (groupId == null) {
			throw new GroupException("get group failed");
		}
		Registration registration = new Registration(matchRegistor.getMatchId(), userId, groupId);
		line = registrationDao.addRegistration(registration);
		if (line != 1) {
			throw new RegistrationException("join match error");
		}
		return matchDao.getDataSet(matchRegistor.getMatchId());
	}
}
