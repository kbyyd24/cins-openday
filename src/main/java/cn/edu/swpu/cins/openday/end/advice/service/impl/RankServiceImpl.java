package cn.edu.swpu.cins.openday.end.advice.service.impl;

import cn.edu.swpu.cins.openday.end.advice.dao.ScoreDao;
import cn.edu.swpu.cins.openday.end.advice.dao.UserDao;
import cn.edu.swpu.cins.openday.end.advice.model.Rank;
import cn.edu.swpu.cins.openday.end.advice.model.RankMsg;
import cn.edu.swpu.cins.openday.end.advice.model.Regist;
import cn.edu.swpu.cins.openday.end.advice.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RankServiceImpl implements RankService {

	private ScoreDao scoreDao;

	private UserDao userDao;

	private List<Integer> cinsTeams = Arrays.asList(1236,1267,1235,1273,1237);

	@Autowired
	public RankServiceImpl(ScoreDao scoreDao, UserDao userDao) {
		this.scoreDao = scoreDao;
		this.userDao = userDao;
	}

	@Override
	public List<RankMsg> getRankMsg() {
		List<Rank> ranks = scoreDao.getRank();
		int i = 1;
		ranks = filterDuplicationAndCinsTeams(ranks);
		for (Rank rank : ranks) rank.setRank(i++);
		List<Regist> regists = userDao.getMails();
		List<RankMsg> rankMsgs = new LinkedList<>();
		mapRankAndMails(ranks, regists, rankMsgs);
		return rankMsgs;
	}

	private List<Rank> filterDuplicationAndCinsTeams(List<Rank> ranks) {
		HashSet<Integer> idSet = new HashSet<>();
		ranks = ranks
			.stream()
			.filter(rank -> idSet.add(rank.getGroupId()) && !cinsTeams.contains(rank.getGroupId()))
			.collect(Collectors.toList());
		return ranks;
	}

	private void mapRankAndMails(List<Rank> ranks, List<Regist> regists, List<RankMsg> rankMsgs) {
		ranks.forEach(rank -> {
			List<Regist> team = regists
				.stream()
				.filter(mail -> mail.getGroupId() == rank.getGroupId())
				.collect(Collectors.toList());
			List<String> mails = team
				.stream()
				.map(Regist::getMail)
				.collect(Collectors.toList());
			rankMsgs.add(new RankMsg(mails, rank.getRank()));
		});
	}
}
