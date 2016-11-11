package cn.edu.swpu.cins.openday.controller;

import cn.edu.swpu.cins.openday.enums.HttpResultEnum;
import cn.edu.swpu.cins.openday.enums.service.MatchServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.MatchHttpResult;
import cn.edu.swpu.cins.openday.model.http.MatchRegister;
import cn.edu.swpu.cins.openday.model.http.UpMatch;
import cn.edu.swpu.cins.openday.model.persistence.Match;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MatchControllerTest {

	private MatchController controller;

	@Mock
	private MatchService matchService;

	@Before
	public void setUp() throws Exception {
		controller = new MatchController(matchService);
	}

	@Test
	public void test_addMatch_success() throws Exception {
		UpMatch upMatch = new UpMatch();
		when(matchService.addMatch(upMatch)).thenReturn(MatchServiceResultEnum.ADD_SUCCESS);
		MatchHttpResult matchHttpResult = controller.addMatch(upMatch);
		assertThat(matchHttpResult.getCode(), is(HttpResultEnum.ADD_SUCCESS.getCode()));
		verify(matchService).addMatch(upMatch);
	}

	@Test
	public void test_getMatches_success() throws Exception {
		int page = 1;
		List<Match> matches = new ArrayList<>();
		when(matchService.getMatches(page)).thenReturn(matches);
		List<Match> ret = controller.getMatches(page);
		assertThat(ret, is(matches));
		verify(matchService).getMatches(page);
	}

	@Test
	public void test_getMatch_success() throws Exception {
		Match match = mock(Match.class);
		when(matchService.getMatch()).thenReturn(match);
		assertThat(controller.getMatch(), is(match));
		verify(matchService).getMatch();
	}

	@Test
	public void test_joinMatch_success() throws Exception {
		MatchRegister matchRegister = new MatchRegister();
		Match match = new Match();
		when(matchService.joinMatch(matchRegister)).thenReturn(match);
		Match ret = controller.joinMatch(matchRegister);
		assertThat(ret, is(match));
		verify(matchService).joinMatch(matchRegister);
	}
}