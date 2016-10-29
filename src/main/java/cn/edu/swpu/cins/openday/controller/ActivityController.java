package cn.edu.swpu.cins.openday.controller;

import cn.edu.swpu.cins.openday.model.http.ActivityHttpResult;
import cn.edu.swpu.cins.openday.model.http.PostActivity;
import cn.edu.swpu.cins.openday.model.persistence.Activity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("activity")
public class ActivityController {

	@GetMapping("list/{page}")
	public List<Activity> getActivities(@PathVariable("page") int page) {
		return null;
	}

	@PostMapping("add")
	public ActivityHttpResult addActivity(@RequestBody PostActivity activity) {
		return null;
	}
}
