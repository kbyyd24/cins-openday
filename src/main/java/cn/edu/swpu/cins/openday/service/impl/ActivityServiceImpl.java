package cn.edu.swpu.cins.openday.service.impl;

import cn.edu.swpu.cins.openday.dao.persistence.ActivityDao;
import cn.edu.swpu.cins.openday.model.persistence.Activity;
import cn.edu.swpu.cins.openday.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
