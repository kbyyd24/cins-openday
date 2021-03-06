package cn.edu.swpu.cins.openday.controller;

import cn.edu.swpu.cins.openday.enums.HttpResultEnum;
import cn.edu.swpu.cins.openday.enums.service.MatchServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.MatchRegister;
import cn.edu.swpu.cins.openday.model.http.RankResult;
import cn.edu.swpu.cins.openday.model.http.TeamMsg;
import cn.edu.swpu.cins.openday.model.persistence.Match;
import cn.edu.swpu.cins.openday.service.FileService;
import cn.edu.swpu.cins.openday.service.MatchService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

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
	public void test_getMatch_success() throws Exception {
		Match match = mock(Match.class);
		Integer id = 1;
		when(matchService.getMatch()).thenReturn(match);
		when(match.getId()).thenReturn(id);
		assertThat(controller.getMatch().get("id"), is(id));
		verify(matchService).getMatch();
	}

	@Test
	public void test_joinMatch_success() throws Exception {
		MatchRegister matchRegister = new MatchRegister();
		int captainId = 1;
		int matchId = 1;
		when(matchService.joinMatch(matchRegister, captainId, matchId)).thenReturn(MatchServiceResultEnum.JOIN_SUCCESS);
		controller.joinMatch(matchRegister, captainId, matchId);
		verify(matchService).joinMatch(matchRegister, captainId, matchId);
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
		TeamMsg teamMsg = mock(TeamMsg.class);
		int userId = 1;
		int matchId = 1;
		when(matchService.getTeamMsg(matchId, userId)).thenReturn(teamMsg);
		assertThat(controller.getTeamMsg(matchId, userId), is(teamMsg));
		verify(matchService).getTeamMsg(matchId, userId);
	}

	@Test
	public void test_uploadAnswer_success() throws Exception {
		int regisId = 1;
		int matchId = 1;
		int userId = 1;
		when(matchService.getGroupId(matchId, userId)).thenReturn(regisId);
		MultipartFile multipartFile = mock(MultipartFile.class);
		when(fileService.saveFile(multipartFile, regisId))
			.thenReturn(MatchServiceResultEnum.SAVE_SUCCESS);
		assertThat(controller.uploadAnswer(multipartFile, matchId, userId).getCode(), is(HttpResultEnum.SAVE_ANSWER_SUCCESS.getCode()));
		verify(matchService).getGroupId(matchId, userId);
		verify(fileService).saveFile(multipartFile, regisId);
	}
}