package cn.edu.swpu.cins.openday.service;

import cn.edu.swpu.cins.openday.enums.service.ActivityServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.PostActivity;
import cn.edu.swpu.cins.openday.model.persistence.Activity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ActivityService {
	List<Activity> getActivities(int page);

	ActivityServiceResultEnum addActivity(PostActivity postActivity);
}
