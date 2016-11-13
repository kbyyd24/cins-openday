package cn.edu.swpu.cins.openday.service.impl;

import cn.edu.swpu.cins.openday.dao.persistence.*;
import cn.edu.swpu.cins.openday.enums.service.MatchServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.MatchRegister;
import cn.edu.swpu.cins.openday.model.http.TeamMsgGetter;
import cn.edu.swpu.cins.openday.model.http.UpMatch;
import cn.edu.swpu.cins.openday.model.persistence.Group;
import cn.edu.swpu.cins.openday.model.persistence.Match;
import cn.edu.swpu.cins.openday.model.persistence.Registration;
import cn.edu.swpu.cins.openday.model.persistence.User;
import cn.edu.swpu.cins.openday.model.service.TeammateMsg;
import cn.edu.swpu.cins.openday.service.MatchService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MatchServiceImplTest {
	
	@Mock
	private MatchDao matchDao;

	@Mock
	private UserDao userDao;

	@Mock
	private GroupDao groupDao;

	@Mock
	private RegistrationDao registrationDao;

	@Mock
	private ScoreDao scoreDao;

	private MatchService service;

	@Before
	public void setUp() throws Exception {
		service = new MatchServiceImpl(matchDao, userDao, groupDao, registrationDao, scoreDao);
	}

	@Test
	public void test_addMatch_success() throws Exception {
		UpMatch upMatch = new UpMatch();
		when(matchDao.addMatch(upMatch)).thenReturn(1);
		MatchServiceResultEnum result = service.addMatch(upMatch);
		assertThat(result, is(MatchServiceResultEnum.ADD_SUCCESS));
		verify(matchDao).addMatch(upMatch);
	}

	@Test
	public void test_getMatches_success() throws Exception {
		int page = 1;
		int limit = 0;
		int offset = 4;
		List<Match> matches = new ArrayList<>();
		when(matchDao.getMatches(limit, offset)).thenReturn(matches);
		List<Match> ret = service.getMatches(page);
		assertThat(ret, is(matches));
		verify(matchDao).getMatches(limit, offset);
	}

	@Test
	public void test_getMatch_success() throws Exception {
		Match match = mock(Match.class);
		when(matchDao.getMatch()).thenReturn(match);
		Match ret = service.getMatch();
		assertThat(ret, is(match));
		verify(matchDao).getMatch();
	}

	@Test
	public void test_joinMatch_success() throws Exception {
		String mail = "mail@mail.com";
		MatchRegister matchRegister = new MatchRegister();
		matchRegister.setMail1(mail);
		matchRegister.setMail2(mail);
		int id = 1;
		matchRegister.setMatchId(id);
		List<User> users = new ArrayList<>();
		User user = new User(id);
		users.add(user);
		users.add(user);
		when(userDao.getIds(mail, mail)).thenReturn(users);
		when(groupDao.addGroup(any(Group.class))).thenReturn(1);
		when(groupDao.getGroupId(any(Group.class))).thenReturn(id);
		when(registrationDao.addRegistration(any(Registration.class))).thenReturn(1);
		MatchServiceResultEnum resultEnum = service.joinMatch(matchRegister);
		assertThat(resultEnum, is(MatchServiceResultEnum.JOIN_SUCCESS));
		verify(userDao).getIds(mail, mail);
		verify(groupDao).addGroup(any(Group.class));
		verify(groupDao).getGroupId(any(Group.class));
		verify(registrationDao, times(2)).addRegistration(any(Registration.class));
	}

	@Test
	public void test_getDataSet_success() throws Exception {
		int id = 1;
		Match match = mock(Match.class);
		when(matchDao.getDataSet(id)).thenReturn(match);
		assertThat(service.getDataSet(id), is(match));
		verify(matchDao).getDataSet(id);
	}

	@Test
	public void test_getRankList_success() throws Exception {
		List scores = mock(List.class);
		when(scoreDao.getAll()).thenReturn(scores);
		doNothing().when(scores).forEach(any(Consumer.class));
		service.getRankList();
		verify(scoreDao).getAll();
		verify(scores).forEach(any(Consumer.class));
	}

	@Test
	public void test_getTeamMsg_success() throws Exception {
		int captainId = 1;
		int matchId = 1;
		Integer groupId = 1;
		when(registrationDao.getGroupId(matchId, captainId)).thenReturn(groupId);
		String groupName = "groupName";
		when(groupDao.getGroupName(groupId)).thenReturn(groupName);
		TeammateMsg tm = mock(TeammateMsg.class);
		when(registrationDao.getTeammateMsg(matchId, captainId, groupId)).thenReturn(tm);
		when(tm.getCaptain()).thenReturn(false);
		Integer userId = 2;
		when(tm.getId()).thenReturn(userId);
		List users = mock(List.class);
		when(userDao.getTeammateMsgs(captainId, userId)).thenReturn(users);
		when(users.size()).thenReturn(2);
		User user = mock(User.class);
		when(users.get(anyInt())).thenReturn(user);
		when(user.getId()).thenReturn(userId);
		TeamMsgGetter tmg = new TeamMsgGetter(captainId, matchId);
		service.getTeamMsg(tmg);

		verify(registrationDao).getGroupId(matchId, captainId);
		verify(groupDao).getGroupName(groupId);
		verify(registrationDao).getTeammateMsg(matchId, captainId, groupId);
		verify(tm, times(2)).getId();
		verify(userDao).getTeammateMsgs(captainId, userId);
		verify(users).size();
		verify(users).get(0);
		verify(user).getId();
		verify(tm).getCaptain();
		verify(user, times(2)).getUsername();
		verify(user, times(2)).getMail();
		verify(users).get(1);

	}

	@Test
	public void test_getRegistId_success() throws Exception {
		int registId = 1;
		int userId = 1;
		int matchId = 1;
		Registration registration = mock(Registration.class);
		when(registrationDao.getRegistration(matchId, userId)).thenReturn(registration);
		when(registration.getId()).thenReturn(registId);
		assertThat(service.getRegistId(matchId, userId), is(registId));
		verify(registrationDao).getRegistration(matchId, userId);
		verify(registration).getId();
	}
}