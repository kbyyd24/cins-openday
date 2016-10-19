package cn.edu.swpu.cins.openday.controller;

import cn.edu.swpu.cins.openday.enums.http.UserHttpResultEnum;
import cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.User;
import cn.edu.swpu.cins.openday.model.http.UserHttpResult;
import cn.edu.swpu.cins.openday.model.service.AuthenticatingUser;
import cn.edu.swpu.cins.openday.service.CacheService;
import cn.edu.swpu.cins.openday.service.MailService;
import cn.edu.swpu.cins.openday.service.UserService;
import cn.edu.swpu.cins.openday.util.MailFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum.ADD_AUTHENTICATING_USER_SUCCESS;
import static cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum.ADD_USER_SUCCESS;

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
	public UserHttpResult signUp(User user) {
		UserServiceResultEnum signUpResult = userService.signUp(user);
		if (signUpResult == ADD_USER_SUCCESS) {
			String token = String.valueOf(System.currentTimeMillis());
			UserServiceResultEnum cacheResult = cacheAuthingUser(user, token);
			if (cacheResult == ADD_AUTHENTICATING_USER_SUCCESS) {
				String subject = MailFormatUtil.getSignUpSubject(user.getUsername());
				String text = MailFormatUtil.getSignUpContent(user.getMail(), token);
				mailService.send(user.getMail(), subject, text);
				return new UserHttpResult(UserHttpResultEnum.ADD_USER_SUCCESS);
			}
			// TODO: 16-10-19 deal cache user failed
		}
		// TODO: 16-10-19 deal add user failed
		return null;
	}

	private UserServiceResultEnum cacheAuthingUser(User user, String token) {
		AuthenticatingUser authenticatingUser = new AuthenticatingUser(user.getMail(), token);
		return cacheService.saveAuthingUser(authenticatingUser);
	}
}
