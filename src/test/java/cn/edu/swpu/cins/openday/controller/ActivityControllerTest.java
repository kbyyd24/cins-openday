package cn.edu.swpu.cins.openday.controller;

import cn.edu.swpu.cins.openday.enums.ActivityHttpResultEnum;
import cn.edu.swpu.cins.openday.enums.service.ActivityServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.ActivityHttpResult;
import cn.edu.swpu.cins.openday.model.http.PostActivity;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ActivityControllerTest {

	@Mock
	private ActivityService activityService;

	private ActivityController controller;

	@Before
	public void setUp() throws Exception {
		controller = new ActivityController(activityService);
	}

	@Test
	public void test_get_list_success() throws Exception {
		int page = 1;
		ArrayList<Activity> activities = new ArrayList<>();
		when(activityService.getActivities(page)).thenReturn(activities);
		List<Activity> ret = controller.getActivities(page);
		assertThat(ret, is(activities));
		verify(activityService).getActivities(page);
	}

	@Test
	public void test_add_activity_success() throws Exception {
		PostActivity postActivity = new PostActivity();
		when(activityService.addActivity(postActivity)).thenReturn(ActivityServiceResultEnum.SAVE_SUCCESS);
		ActivityHttpResult ret = controller.addActivity(postActivity);
		assertThat(ret.getCode(), is(ActivityHttpResultEnum.SAVE_SUCCESS.getCode()));
		verify(activityService).addActivity(postActivity);
	}
}