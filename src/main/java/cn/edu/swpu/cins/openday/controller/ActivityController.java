package cn.edu.swpu.cins.openday.controller;

import cn.edu.swpu.cins.openday.enums.HttpResultEnum;
import cn.edu.swpu.cins.openday.enums.service.ActivityServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.ActivityHttpResult;
import cn.edu.swpu.cins.openday.model.http.PostActivity;
import cn.edu.swpu.cins.openday.model.persistence.Activity;
import cn.edu.swpu.cins.openday.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("activity")
public class ActivityController {

	private ActivityService activityService;

	@Autowired
	public ActivityController(ActivityService activityService) {
		this.activityService = activityService;
	}

	@GetMapping("list/{page}")
	public List<Activity> getActivities(@PathVariable("page") int page) {
		return activityService.getActivities(page);
	}

	@PostMapping("add")
	public ActivityHttpResult addActivity(@RequestBody PostActivity activity) {
		ActivityServiceResultEnum resultEnum = activityService.addActivity(activity);
		if (resultEnum == ActivityServiceResultEnum.SAVE_SUCCESS) {
			return new ActivityHttpResult(HttpResultEnum.SAVE_SUCCESS);
		}
		// TODO: 16-10-30 deal other failure of save
		return new ActivityHttpResult(HttpResultEnum.SAVE_FAILED);
	}
}
