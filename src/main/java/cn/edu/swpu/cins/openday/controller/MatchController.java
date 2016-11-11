package cn.edu.swpu.cins.openday.controller;

import cn.edu.swpu.cins.openday.enums.HttpResultEnum;
import cn.edu.swpu.cins.openday.enums.service.MatchServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.MatchHttpResult;
import cn.edu.swpu.cins.openday.model.http.MatchRegister;
import cn.edu.swpu.cins.openday.model.http.RankResult;
import cn.edu.swpu.cins.openday.model.http.UpMatch;
import cn.edu.swpu.cins.openday.model.persistence.Match;
import cn.edu.swpu.cins.openday.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
			return new MatchHttpResult(HttpResultEnum.ADD_SUCCESS);
		}
		return new MatchHttpResult(HttpResultEnum.ADD_FAILED);
	}

	@GetMapping("list/{page}")
	public List<Match> getMatches(@PathVariable(required = false) Integer page) {
		if (page == null || page < 1) {
			page = 1;
		}
		return matchService.getMatches(page);
	}

	@GetMapping
	public Match getMatch() {
		return matchService.getMatch();
	}

	@PostMapping("join")
		public MatchHttpResult joinMatch(@RequestBody MatchRegister matchRegister) {
		MatchServiceResultEnum resultEnum = matchService.joinMatch(matchRegister);
		if (resultEnum == MatchServiceResultEnum.JOIN_SUCCESS) {
			return new MatchHttpResult(HttpResultEnum.JOIN_SUCCESS);
		}
		return new MatchHttpResult(HttpResultEnum.JOIN_FAILED);
	}

	@GetMapping("dataSet")
	public Match getDataSet(@RequestParam int id) {
		return matchService.getDataSet(id);
	}

	@GetMapping("rankList")
	public RankResult getRankList() {
		return matchService.getRankList();
	}
}
