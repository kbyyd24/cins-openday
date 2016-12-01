package cn.edu.swpu.cins.openday.end.advice.components;

import cn.edu.swpu.cins.openday.end.advice.model.RankMsg;
import cn.edu.swpu.cins.openday.end.advice.service.MailFormatService;
import cn.edu.swpu.cins.openday.end.advice.service.MailService;
import cn.edu.swpu.cins.openday.end.advice.service.RankService;
import cn.edu.swpu.cins.openday.end.advice.service.impl.FailedMailFormatService;
import cn.edu.swpu.cins.openday.end.advice.service.impl.SuccessMailFormatService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class MailAdvice {

	private final Log logger = LogFactory.getLog(this.getClass());

	private MailService mailService;

	private RankService rankService;

	private SuccessMailFormatService successMailFormatService;

	private FailedMailFormatService failedMailFormatService;

	@Autowired
	public MailAdvice(MailService mailService, RankService rankService,
	                  SuccessMailFormatService successMailFormatService,
	                  FailedMailFormatService failedMailFormatService) {
		this.mailService = mailService;
		this.rankService = rankService;
		this.successMailFormatService = successMailFormatService;
		this.failedMailFormatService = failedMailFormatService;
	}

	@Value("#{'${openDay.mail.advice.cc}'.split(',')}")
	private List<String> cc;

	@Scheduled(cron = "0 30 21 1 DEC ?")
	public void sendAdvices() throws MessagingException {
		List<RankMsg> rankMsgs = rankService.getRankMsg();
		ArrayList<String> finishListMails = new ArrayList<>(20);
		ArrayList<String> otherMails = new ArrayList<>();
		mapRank(rankMsgs, finishListMails, otherMails);
		logger.info("finishListMails : " + finishListMails);
		logger.info("otherMails : " + otherMails);
		sendSuccess(successMailFormatService, finishListMails);
		sendFailed(failedMailFormatService, otherMails);
		logger.info("mail sent");
	}

	private void mapRank(List<RankMsg> rankMsgs, ArrayList<String> finishListMails, ArrayList<String> otherMails) {
		Predicate<RankMsg> filter = msg -> msg.getRank() < 11;
		Map<Boolean, List<RankMsg>> userMap =
			rankMsgs.
				stream().
				collect(Collectors.partitioningBy(filter));
		userMap.get(true)
			.forEach(rankMsg ->
				rankMsg.getMails()
					.forEach(finishListMails::add));
		userMap.get(false)
			.forEach(rankMsg ->
				rankMsg.getMails().forEach(otherMails::add));
	}

	private void sendFailed(MailFormatService mailFormatService, ArrayList<String> otherMails) throws MessagingException {
		String subject = mailFormatService.getSubject();
		String content = mailFormatService.getContent();
		mailService.sendBatchMailWithCC(subject, content, cc, otherMails);
	}

	private void sendSuccess(MailFormatService mailFormatService, ArrayList<String> finishListMails) throws MessagingException {
		String subject = mailFormatService.getSubject();
		String content = mailFormatService.getContent();
		mailService.sendBatchMailWithCC(subject, content, cc, finishListMails);
	}

}
