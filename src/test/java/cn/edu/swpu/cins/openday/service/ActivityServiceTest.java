package cn.edu.swpu.cins.openday.service;

import cn.edu.swpu.cins.openday.dao.persistence.ActivityDao;
import cn.edu.swpu.cins.openday.enums.service.ActivityServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.PostActivity;
import cn.edu.swpu.cins.openday.model.persistence.Activity;
import cn.edu.swpu.cins.openday.service.impl.ActivityServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ActivityServiceTest {

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

	@Test
	public void test_addActivity_success() throws Exception {
		PostActivity postActivity = new PostActivity();
		when(activityDao.addActivity(postActivity)).thenReturn(1);
		ActivityServiceResultEnum resultEnum = service.addActivity(postActivity);
		assertThat(resultEnum, is(ActivityServiceResultEnum.SAVE_SUCCESS));
		verify(activityDao).addActivity(postActivity);
	}
}