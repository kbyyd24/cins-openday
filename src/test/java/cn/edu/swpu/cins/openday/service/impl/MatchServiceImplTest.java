package cn.edu.swpu.cins.openday.service.impl;

import cn.edu.swpu.cins.openday.dao.persistence.GroupDao;
import cn.edu.swpu.cins.openday.dao.persistence.MatchDao;
import cn.edu.swpu.cins.openday.dao.persistence.RegistrationDao;
import cn.edu.swpu.cins.openday.dao.persistence.UserDao;
import cn.edu.swpu.cins.openday.enums.service.MatchServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.MatchRegister;
import cn.edu.swpu.cins.openday.model.http.UpMatch;
import cn.edu.swpu.cins.openday.model.persistence.Group;
import cn.edu.swpu.cins.openday.model.persistence.Match;
import cn.edu.swpu.cins.openday.model.persistence.Registration;
import cn.edu.swpu.cins.openday.service.MatchService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

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

	private MatchService service;

	@Before
	public void setUp() throws Exception {
		service = new MatchServiceImpl(matchDao, userDao, groupDao, registrationDao);
	}

	@Test
	public void test_addMatch_success() throws Exception {
		String matchName = "match name";
		String detail = "detail";
		Long startTime = 1L;
		Long endTime = 2L;
		UpMatch upMatch = new UpMatch(matchName, detail, startTime, endTime);
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
	public void test_joinMatch_success() throws Exception {
		String mail = "mail@mail.com";
		MatchRegister matchRegister = new MatchRegister();
		matchRegister.setMail(mail);
		int id = 1;
		matchRegister.setMatchId(id);
		when(userDao.getId(mail)).thenReturn(id);
		when(groupDao.addGroup(any(Group.class))).thenReturn(1);
		when(groupDao.getGroupId(any(Group.class))).thenReturn(id);
		when(registrationDao.addRegistration(any(Registration.class))).thenReturn(1);
		Match match = mock(Match.class);
		when(matchDao.getDataSet(id)).thenReturn(match);
		Match ret = service.joinMatch(matchRegister);
		assertThat(ret, is(match));
		verify(userDao).getId(mail);
		verify(groupDao).addGroup(any(Group.class));
		verify(groupDao).getGroupId(any(Group.class));
		verify(registrationDao).addRegistration(any(Registration.class));
		verify(matchDao).getDataSet(id);
	}
}