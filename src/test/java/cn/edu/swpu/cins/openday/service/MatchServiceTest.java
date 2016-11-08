package cn.edu.swpu.cins.openday.service;

import cn.edu.swpu.cins.openday.dao.persistence.MatchDao;
import cn.edu.swpu.cins.openday.enums.service.MatchServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.UpMatch;
import cn.edu.swpu.cins.openday.service.impl.MatchServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MatchServiceTest {
	
	@Mock
	private MatchDao matchDao;

	private MatchService service;

	@Before
	public void setUp() throws Exception {
		service = new MatchServiceImpl(matchDao);
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
}