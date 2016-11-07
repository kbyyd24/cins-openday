package cn.edu.swpu.cins.openday.controller;

import cn.edu.swpu.cins.openday.enums.http.MatchHttpResultEnum;
import cn.edu.swpu.cins.openday.enums.service.MatchServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.MatchHttpResult;
import cn.edu.swpu.cins.openday.model.http.UpMatch;
import cn.edu.swpu.cins.openday.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("match")
public class MatchController {
	private MatchService matchService;

	@Autowired
	public MatchController(MatchService matchService) {
		this.matchService = matchService;
	}

	@PostMapping("add")
	public MatchHttpResult addMatch(@RequestBody UpMatch upMatch) {
		MatchServiceResultEnum addResult = matchService.addMatch(upMatch);
		if (addResult == MatchServiceResultEnum.ADD_SUCCESS) {
			return new MatchHttpResult(MatchHttpResultEnum.ADD_SUCCESS);
		}
		return new MatchHttpResult(MatchHttpResultEnum.ADD_FAILED);
	}
}
