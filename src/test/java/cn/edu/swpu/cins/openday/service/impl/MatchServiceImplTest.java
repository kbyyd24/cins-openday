package cn.edu.swpu.cins.openday.service.impl;

import cn.edu.swpu.cins.openday.dao.persistence.*;
import cn.edu.swpu.cins.openday.enums.service.MatchServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.MatchRegister;
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
	public void test_getMatch_success() throws Exception {
		Match match = mock(Match.class);
		when(matchDao.getMatch()).thenReturn(match);
		Match ret = service.getMatch();
		assertThat(ret, is(match));
		verify(matchDao).getMatch();
	}

	@Test
	public void test_joinMatch_success() throws Exception {
		String teammate = "mail";
		User user = mock(User.class);
		when(userDao.getUser(teammate)).thenReturn(user);
		String username = "username";
		when(user.getUsername()).thenReturn(username);
		Integer line = 1;
		when(groupDao.addGroup(any(Group.class))).thenReturn(line);
		Group group = mock(Group.class);
		when(groupDao.getGroupId(any(Group.class))).thenReturn(group);
		Integer teammateId = 1;
		when(group.getId()).thenReturn(teammateId);
		when(registrationDao.addRegistration(any(Registration.class))).thenReturn(line);
		String groupName = "group name";
		MatchRegister matchRegister = new MatchRegister(teammate, groupName);
		int captainId = 1;
		int matchId = 1;
		assertThat(service.joinMatch(matchRegister, captainId, matchId),
			is(MatchServiceResultEnum.JOIN_SUCCESS));
		verify(userDao).getUser(teammate);
		verify(user).getUsername();
		verify(groupDao).addGroup(any(group.getClass()));
		verify(groupDao).getGroupId(any(group.getClass()));
		verify(group, times(3)).getId();
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
		service.getTeamMsg(matchId, captainId);

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