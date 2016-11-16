package cn.edu.swpu.cins.openday.service;

import cn.edu.swpu.cins.openday.model.persistence.Activity;

import java.util.List;

public interface ActivityService {
	List<Activity> getActivities(int page);

}
