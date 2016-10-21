package cn.edu.swpu.cins.openday.controller;

import cn.edu.swpu.cins.openday.enums.CacheResultEnum;
import cn.edu.swpu.cins.openday.enums.http.UserHttpResultEnum;
import cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.SignUpUser;
import cn.edu.swpu.cins.openday.model.http.UserHttpResult;
import cn.edu.swpu.cins.openday.model.service.AuthenticatingUser;
import cn.edu.swpu.cins.openday.service.CacheService;
import cn.edu.swpu.cins.openday.service.MailService;
import cn.edu.swpu.cins.openday.service.UserService;
import cn.edu.swpu.cins.openday.util.MailFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

import static cn.edu.swpu.cins.openday.enums.CacheResultEnum.SAVE_SUCCESS;
import static cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum.*;

@RestController
@RequestMapping("user")
public class UserController {
	private UserService userService;
	private MailService mailService;
	private CacheService cacheService;

	@Autowired
	public UserController(UserService userService, MailService mailService, CacheService cacheService) {
		this.userService = userService;
		this.mailService = mailService;
		this.cacheService = cacheService;
	}

	@PostMapping("signup")
	public UserHttpResult signUp(@RequestBody SignUpUser signUpUser) {
		UserServiceResultEnum signUpResult = userService.signUp(signUpUser);
		if (signUpResult == ADD_USER_SUCCESS) {
			String token = String.valueOf(System.currentTimeMillis());
			CacheResultEnum cacheResult = cacheAuthingUser(signUpUser, token);
			if (cacheResult == SAVE_SUCCESS) {
				String subject = MailFormatUtil.getSignUpSubject(signUpUser.getUsername());
				String text = MailFormatUtil.getSignUpContent(signUpUser.getMail(), token);
				try {
					mailService.send(signUpUser.getMail(), subject, text);
				} catch (MessagingException e) {
					// TODO: 16-10-19 deal exception
					e.printStackTrace();
				}
				return new UserHttpResult(UserHttpResultEnum.ADD_USER_SUCCESS);
			} else {
				// TODO: 16-10-19 deal cache signUpUser failed
			}
		}
		return returnSignUpError(signUpResult);
	}

	private UserHttpResult returnSignUpError(UserServiceResultEnum signUpResult) {
		if (signUpResult == ADD_USER_FAILED) {
			return new UserHttpResult(UserHttpResultEnum.ADD_USER_FAILED);
		} else if (signUpResult == EXISTED_USERNAME_AND_MAIL) {
			return new UserHttpResult(UserHttpResultEnum.EXISTED_USERNAME_AND_MAIL);
		} else if (signUpResult == EXISTED_USERNAME) {
			return new UserHttpResult(UserHttpResultEnum.EXISTED_USERNAME);
		} else if (signUpResult == EXISTED_MAIL) {
			return new UserHttpResult(UserHttpResultEnum.EXISTED_MALI);
		}
		return new UserHttpResult(UserHttpResultEnum.UNKNOWN_ERROR);
	}

	private CacheResultEnum cacheAuthingUser(SignUpUser signUpUser, String token) {
		AuthenticatingUser authenticatingUser = new AuthenticatingUser(signUpUser.getMail(), token);
		return cacheService.saveAuthingUser(authenticatingUser);
	}
}
