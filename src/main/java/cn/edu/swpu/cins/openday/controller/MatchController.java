package cn.edu.swpu.cins.openday.controller;

import cn.edu.swpu.cins.openday.enums.HttpResultEnum;
import cn.edu.swpu.cins.openday.enums.service.MatchServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.*;
import cn.edu.swpu.cins.openday.model.persistence.Match;
import cn.edu.swpu.cins.openday.service.FileService;
import cn.edu.swpu.cins.openday.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("match")
public class MatchController {
	private MatchService matchService;
	private FileService fileService;

	@Autowired
	public MatchController(MatchService matchService, FileService fileService) {
		this.matchService = matchService;
		this.fileService = fileService;
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

	@PostMapping("team")
	public TeamMsg getTeamMsg(@RequestBody TeamMsgGetter teamMsgGetter) {
		return matchService.getTeamMsg(teamMsgGetter);
	}

	@PostMapping("upload")
	public MatchHttpResult uploadAnswer(@RequestParam("answer") MultipartFile file,
	                                    @RequestHeader("openday-user") String mail)
		throws IOException, ServletException {
		int registId = matchService.getRegistId(mail);
		MatchServiceResultEnum saveAnswer = fileService.saveFile(file, registId);
		if (saveAnswer == MatchServiceResultEnum.SAVE_SUCCESS) {
			return new MatchHttpResult(HttpResultEnum.SAVE_ANSWER_SUCCESS);
		}
		return new MatchHttpResult(HttpResultEnum.SAVE_ANSWER_FAILED);
	}
}
