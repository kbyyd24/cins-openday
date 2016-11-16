package cn.edu.swpu.cins.openday.service.impl;

import cn.edu.swpu.cins.openday.dao.persistence.ActivityDao;
import cn.edu.swpu.cins.openday.model.persistence.Activity;
import cn.edu.swpu.cins.openday.service.ActivityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ActivityServiceImplTest {

	@Mock
	private ActivityDao activityDao;

	private ActivityService service;

	@Before
	public void setUp() throws Exception {
		service = new ActivityServiceImpl(activityDao);
	}

	@Test
	public void test_getActivities_success() throws Exception {
		ArrayList<Activity> activities = new ArrayList<>();
		activities.add(new Activity());
		int page = 1;
		int offset = 4;
		int limit = 0;
		when(activityDao.getActivities(limit, offset)).thenReturn(activities);
		List<Activity> ret = service.getActivities(page);
		assertThat(ret, is(activities));
		verify(activityDao, times(1)).getActivities(limit, offset);
	}

}