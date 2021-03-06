package cn.edu.swpu.cins.openday.service.impl;

import cn.edu.swpu.cins.openday.dao.persistence.*;
import cn.edu.swpu.cins.openday.enums.ExceptionMsgEnum;
import cn.edu.swpu.cins.openday.enums.service.MatchServiceResultEnum;
import cn.edu.swpu.cins.openday.exception.GroupException;
import cn.edu.swpu.cins.openday.exception.OpenDayException;
import cn.edu.swpu.cins.openday.exception.RegistrationException;
import cn.edu.swpu.cins.openday.exception.UserException;
import cn.edu.swpu.cins.openday.model.http.MatchRegister;
import cn.edu.swpu.cins.openday.model.http.Rank;
import cn.edu.swpu.cins.openday.model.http.RankResult;
import cn.edu.swpu.cins.openday.model.http.TeamMsg;
import cn.edu.swpu.cins.openday.model.persistence.Group;
import cn.edu.swpu.cins.openday.model.persistence.Match;
import cn.edu.swpu.cins.openday.model.persistence.Registration;
import cn.edu.swpu.cins.openday.model.persistence.User;
import cn.edu.swpu.cins.openday.model.service.ScoreRank;
import cn.edu.swpu.cins.openday.model.service.TeammateMsg;
import cn.edu.swpu.cins.openday.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchServiceImpl implements MatchService {
	private MatchDao matchDao;
	private UserDao userDao;
	private GroupDao groupDao;
	private RegistrationDao registrationDao;
	private ScoreDao scoreDao;

	@Autowired
	public MatchServiceImpl(MatchDao matchDao, UserDao userDao, GroupDao groupDao, RegistrationDao registrationDao, ScoreDao scoreDao) {
		this.matchDao = matchDao;
		this.userDao = userDao;
		this.groupDao = groupDao;
		this.registrationDao = registrationDao;
		this.scoreDao = scoreDao;
	}

	@Override
	public Match getMatch() {
		return matchDao.getMatch();
	}

	@Override
	@Transactional(rollbackFor = {DataAccessException.class, SQLException.class, OpenDayException.class})
	public MatchServiceResultEnum joinMatch(MatchRegister matchRegister, int captainId, int matchId) {
		User user = userDao.getUser(matchRegister.getTeammate());
		if (user == null || user.getUsername() == null) {
			throw new UserException(ExceptionMsgEnum.NO_TEAMMATE_EXIST);
		}
		Group group = new Group(matchRegister.getGroupName(), matchId);
		int line = groupDao.addGroup(group);
		if (line != 1) {
			throw new GroupException(ExceptionMsgEnum.ADD_TEAM_ERROR);
		}
		Group groupWithId = groupDao.getGroupId(group);
		if (groupWithId == null || groupWithId.getId() == null || groupWithId.getId() == 0) {
			throw new GroupException(ExceptionMsgEnum.GET_TEAM_ID_FAILED);
		}
		ArrayList<Registration> registrations = new ArrayList<>();
		registrations.add(new Registration(matchId, captainId, groupWithId.getId(), true));
		registrations.add(new Registration(matchId, user.getId(), groupWithId.getId(), false));
		line = registrationDao.addTeam(registrations);
		if (line != 2) {
			throw new RegistrationException(ExceptionMsgEnum.JOIN_MATCH_FAILED);
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

	@Override
	public int getGroupId(int matchId, int userId) {
		return registrationDao.getGroupId(matchId, userId);
	}

	@Override
	public TeamMsg getTeamMsg(int matchId, int userId) {
		int groupId = registrationDao.getGroupId(matchId, userId);
		if (groupId == -1) {
			throw new GroupException(ExceptionMsgEnum.NO_TEAM_FOUND);
		}
		String groupName = groupDao.getGroupName(groupId);
		TeammateMsg teammateMsg =
			registrationDao.getTeammateMsg(matchId, userId, groupId);
		List<User> users = userDao.getTeammateMsgs(userId, teammateMsg.getId());
		if (users.size() != 2) {
			return new TeamMsg();
		}
		TeamMsg teamMsg = new TeamMsg();
		teamMsg.setTeamName(groupName);
		setTeamMsg(teammateMsg, users, teamMsg);
		return teamMsg;
	}

	private void setTeamMsg(TeammateMsg teammateMsg, List<User> users, TeamMsg teamMsg) {
		User user = users.get(0);
		if (user.getId() == teammateMsg.getId()) {
			if (teammateMsg.getCaptain()) {
				setCaptainFirst(users, user, teamMsg);
			} else {
				setMemberFirst(users, user, teamMsg);
			}
		} else {
			if (teammateMsg.getCaptain()) {
				setMemberFirst(users, user, teamMsg);
			} else {
				setCaptainFirst(users, user, teamMsg);
			}
		}
	}

	private void setMemberFirst(List<User> users, User user, TeamMsg teamMsg) {
		setMember(user, teamMsg);
		user = users.get(1);
		setCaptain(user, teamMsg);
	}

	private void setCaptain(User user, TeamMsg teamMsg) {
		teamMsg.setCaptain(user.getUsername());
		teamMsg.setCaptainMail(user.getMail());
	}

	private void setMember(User user, TeamMsg teamMsg) {
		teamMsg.setTeamMember(user.getUsername());
		teamMsg.setTeamMemberMail(user.getMail());
	}

	private void setCaptainFirst(List<User> users, User user, TeamMsg teamMsg) {
		setCaptain(user, teamMsg);
		user = users.get(1);
		setMember(user, teamMsg);
	}

	private List<Rank> filterDuplication(List<ScoreRank> all) {
		HashSet<Integer> groupSet = new HashSet<>();
		return all.stream()
				.filter(scoreRank -> groupSet.add(scoreRank.getId()))
				.map(Rank::new)
				.collect(Collectors.toList());
	}

	private void setRank(List<Rank> ranks) {
		for (int i = 0; i < ranks.size();) {
			ranks.get(i).setRank(++i);
		}
	}
}
