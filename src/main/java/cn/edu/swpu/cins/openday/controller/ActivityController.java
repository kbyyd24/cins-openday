package cn.edu.swpu.cins.openday.controller;

import cn.edu.swpu.cins.openday.model.persistence.Activity;
import cn.edu.swpu.cins.openday.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
