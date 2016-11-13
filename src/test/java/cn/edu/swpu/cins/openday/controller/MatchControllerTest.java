package cn.edu.swpu.cins.openday.controller;

import cn.edu.swpu.cins.openday.enums.HttpResultEnum;
import cn.edu.swpu.cins.openday.enums.service.MatchServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.*;
import cn.edu.swpu.cins.openday.model.persistence.Match;
import cn.edu.swpu.cins.openday.service.FileService;
import cn.edu.swpu.cins.openday.service.MatchService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MatchControllerTest {

	private MatchController controller;

	@Mock
	private MatchService matchService;

	@Mock
	private FileService fileService;

	@Before
	public void setUp() throws Exception {
		controller = new MatchController(matchService, fileService);
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
		when(matchService.joinMatch(matchRegister)).thenReturn(MatchServiceResultEnum.JOIN_SUCCESS);
		controller.joinMatch(matchRegister);
		verify(matchService).joinMatch(matchRegister);
	}

	@Test
	public void test_getDataSet_success() throws Exception {
		Match dataSet = mock(Match.class);
		int id = 1;
		when(matchService.getDataSet(id)).thenReturn(dataSet);
		assertThat(controller.getDataSet(id), is(dataSet));
		verify(matchService).getDataSet(id);
	}

	@Test
	public void test_getRankList_success() throws Exception {
		RankResult rankResult = mock(RankResult.class);
		when(matchService.getRankList()).thenReturn(rankResult);
		assertThat(controller.getRankList(), is(rankResult));
		verify(matchService).getRankList();
	}

	@Test
	public void test_getTeamMsg() throws Exception {
		TeamMsgGetter teamMsgGetter = mock(TeamMsgGetter.class);
		TeamMsg teamMsg = mock(TeamMsg.class);
		when(matchService.getTeamMsg(teamMsgGetter)).thenReturn(teamMsg);
		assertThat(controller.getTeamMsg(teamMsgGetter), is(teamMsg));
		verify(matchService).getTeamMsg(teamMsgGetter);
	}

	@Test
	public void test_uploadAnswer_success() throws Exception {
		int regisId = 1;
		String mail = "mail";
		when(matchService.getRegistId(mail)).thenReturn(regisId);
		MultipartFile multipartFile = mock(MultipartFile.class);
		when(fileService.saveFile(multipartFile, regisId))
			.thenReturn(MatchServiceResultEnum.SAVE_SUCCESS);
		assertThat(controller.uploadAnswer(multipartFile, mail).getCode(), is(HttpResultEnum.SAVE_ANSWER_SUCCESS.getCode()));
		verify(matchService).getRegistId(mail);
		verify(fileService).saveFile(multipartFile, regisId);
	}
}