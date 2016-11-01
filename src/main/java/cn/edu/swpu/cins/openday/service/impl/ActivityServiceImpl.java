package cn.edu.swpu.cins.openday.service.impl;

import cn.edu.swpu.cins.openday.dao.persistence.ActivityDao;
import cn.edu.swpu.cins.openday.enums.service.ActivityServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.PostActivity;
import cn.edu.swpu.cins.openday.model.persistence.Activity;
import cn.edu.swpu.cins.openday.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {
	private ActivityDao activityDao;

	@Autowired
	public ActivityServiceImpl(ActivityDao activityDao) {
		this.activityDao = activityDao;
	}

	@Override
	public List<Activity> getActivities(int page) {
		if (page < 1) {
			page = 1;
		}
		final int offset = 4;
		int limit = (page - 1) * offset;
		List<Activity> activities = activityDao.getActivities(limit, offset);
		return activities == null ? new ArrayList<>() : activities;
	}

	@Override
	@Transactional(rollbackFor = DataAccessException.class)
	public ActivityServiceResultEnum addActivity(PostActivity postActivity) {
		int line = activityDao.addActivity(postActivity);
		if (line == 1) {
			return ActivityServiceResultEnum.SAVE_SUCCESS;
		}
		return ActivityServiceResultEnum.SAVE_FAILED;
	}
}
