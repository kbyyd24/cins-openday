package cn.edu.swpu.cins.openday.controller;

import cn.edu.swpu.cins.openday.enums.http.UserHttpResultEnum;
import cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.SignInUser;
import cn.edu.swpu.cins.openday.model.http.SignUpUser;
import cn.edu.swpu.cins.openday.model.http.UserHttpResult;
import cn.edu.swpu.cins.openday.model.service.AuthenticatingUser;
import cn.edu.swpu.cins.openday.model.http.UserSignInResult;
import cn.edu.swpu.cins.openday.service.MailFormatService;
import cn.edu.swpu.cins.openday.service.MailService;
import cn.edu.swpu.cins.openday.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

import static cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum.*;

@RestController
@RequestMapping("user")
public class UserController {
	private UserService userService;
	private MailService mailService;
	private MailFormatService mailFormatService;

	@Autowired
	public UserController(UserService userService, MailService mailService, MailFormatService mailFormatService) {
		this.userService = userService;
		this.mailService = mailService;
		this.mailFormatService = mailFormatService;
	}

	@PostMapping("signup")
	public UserHttpResult signUp(@RequestBody SignUpUser signUpUser) {
		String token = String.valueOf(System.currentTimeMillis());
		UserServiceResultEnum signUpResult = userService.signUp(signUpUser, token);
		if (signUpResult == ADD_USER_SUCCESS) {
			String subject = mailFormatService.getSignUpSubject(signUpUser.getUsername());
			String text = mailFormatService.getSignUpContent(signUpUser.getMail(), token);
			try {
				mailService.send(signUpUser.getMail(), subject, text);
			} catch (MessagingException e) {
				// TODO: 16-10-19 deal exception
				e.printStackTrace();
			}
			return new UserHttpResult(UserHttpResultEnum.ADD_USER_SUCCESS);
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
		} else if (signUpResult == PASSWORD_NOT_SAME) {
			return new UserHttpResult(UserHttpResultEnum.PASSWORD_NOT_SAME);
		}
		return new UserHttpResult(UserHttpResultEnum.UNKNOWN_ERROR);
	}

	@PostMapping("enable")
	public UserHttpResult enable(@RequestBody AuthenticatingUser au) {
		UserServiceResultEnum enableRet = userService.enable(au);
		if (enableRet == ENABLE_TOKEN_SUCCESS) {
			return new UserHttpResult(UserHttpResultEnum.ENABLE_TOKEN_SUCCESS);
		}
		if (enableRet == UserServiceResultEnum.ENABLE_TOKEN_INVALID) {
			return new UserHttpResult(UserHttpResultEnum.ENABLE_TOKEN_INVALID);
		}
		if (enableRet == UserServiceResultEnum.ENABLE_TOKEN_TIMEOUT) {
			return new UserHttpResult(UserHttpResultEnum.ENABLE_TOKEN_TIMEOUT);
		}
		return new UserHttpResult(UserHttpResultEnum.ENABLE_FAILED);
	}

	@PostMapping("signin")
	public UserSignInResult signIn(@RequestBody SignInUser signInUser) {
		UserSignInResult signin = userService.signin(signInUser);
		if (signin.getStatus() != UserServiceResultEnum.LOGIN_SUCCESS) {
			signin.setStatus(UserServiceResultEnum.LOGIN_FAILED);
		}
		return signin;
	}
}
