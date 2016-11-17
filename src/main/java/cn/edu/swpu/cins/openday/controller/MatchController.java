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
import java.util.HashMap;
import java.util.Map;

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

	@GetMapping
	public Map<String, Integer> getMatch() {
		Match match = matchService.getMatch();
		HashMap<String, Integer> ret = new HashMap<>(2);
		ret.put("id", match.getId());
		return ret;
	}

	@PostMapping("join")
		public HttpResult joinMatch(@RequestBody MatchRegister matchRegister,
	                                   @RequestHeader("open-day-user-id") int captainId,
	                                   @RequestHeader("open-day-match-id") int matchId) {
		MatchServiceResultEnum resultEnum = matchService.joinMatch(matchRegister, captainId, matchId);
		if (resultEnum == MatchServiceResultEnum.JOIN_SUCCESS) {
			return new HttpResult(HttpResultEnum.JOIN_SUCCESS);
		}
		return new HttpResult(HttpResultEnum.JOIN_FAILED);
	}

	@GetMapping("dataSet")
	public Match getDataSet(@RequestHeader("open-day-match-id") int matchId) {
		return matchService.getDataSet(matchId);
	}

	@GetMapping("rankList")
	public RankResult getRankList() {
		return matchService.getRankList();
	}

	@GetMapping("team")
	public TeamMsg getTeamMsg(@RequestHeader("open-day-match-id") int matchId,
	                          @RequestHeader("open-day-user-id") int userId) {
		return matchService.getTeamMsg(matchId, userId);
	}

	@PostMapping("upload")
	public HttpResult uploadAnswer(@RequestPart("answer") MultipartFile file,
	                                    @RequestHeader("open-day-user-id") int userId,
	                                    @RequestHeader("open-day-match-id") int matchId)
		throws IOException, ServletException {
		int registId = matchService.getRegistId(matchId, userId);
		if (registId == -1) {
			return new HttpResult(HttpResultEnum.REQUEST_DENY);
		}
		MatchServiceResultEnum saveAnswer = fileService.saveFile(file, registId);
		if (saveAnswer == MatchServiceResultEnum.SAVE_SUCCESS) {
			return new HttpResult(HttpResultEnum.SAVE_ANSWER_SUCCESS);
		}
		return new HttpResult(HttpResultEnum.SAVE_ANSWER_FAILED);
	}
}
