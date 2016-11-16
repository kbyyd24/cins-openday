package cn.edu.swpu.cins.openday.controller;

import cn.edu.swpu.cins.openday.enums.HttpResultEnum;
import cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum;
import cn.edu.swpu.cins.openday.model.http.*;
import cn.edu.swpu.cins.openday.model.service.AuthUser;
import cn.edu.swpu.cins.openday.service.MailFormatService;
import cn.edu.swpu.cins.openday.service.MailService;
import cn.edu.swpu.cins.openday.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

import java.util.Objects;

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
	public HttpResult signUp(@RequestBody SignUpUser signUpUser) {
		String token = String.valueOf(System.currentTimeMillis());
		UserServiceResultEnum signUpResult = userService.signUp(signUpUser, token);
		if (signUpResult == ADD_USER_SUCCESS) {
			String subject = mailFormatService.getSignUpSubject(signUpUser.getUsername());
			String text = mailFormatService.getSignUpContent(signUpUser.getMail(), token);
			try {
				mailService.send(signUpUser.getMail(), subject, text);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			return new HttpResult(HttpResultEnum.SIGN_UP_USER_SUCCESS);
		}
		return returnSignUpError(signUpResult);
	}

	private HttpResult returnSignUpError(UserServiceResultEnum signUpResult) {
		if (signUpResult == ADD_USER_FAILED) {
			return new HttpResult(HttpResultEnum.SIGN_UP_USER_FAILED);
		} else if (signUpResult == EXISTED_USERNAME_AND_MAIL) {
			return new HttpResult(HttpResultEnum.EXISTED_USERNAME_AND_MAIL);
		} else if (signUpResult == EXISTED_USERNAME) {
			return new HttpResult(HttpResultEnum.EXISTED_USERNAME);
		} else if (signUpResult == EXISTED_MAIL) {
			return new HttpResult(HttpResultEnum.EXISTED_MALI);
		} else if (signUpResult == PASSWORD_NOT_SAME) {
			return new HttpResult(HttpResultEnum.PASSWORD_NOT_SAME);
		}
		return new HttpResult(HttpResultEnum.UNKNOWN_ERROR);
	}

	@PostMapping("enable")
	public HttpResult enable(@RequestBody AuthUser au) {
		UserServiceResultEnum enableRet = userService.enable(au);
		if (enableRet == ENABLE_TOKEN_SUCCESS) {
			return new HttpResult(HttpResultEnum.ENABLE_TOKEN_SUCCESS);
		}
		if (enableRet == UserServiceResultEnum.ENABLE_TOKEN_INVALID) {
			return new HttpResult(HttpResultEnum.ENABLE_TOKEN_INVALID);
		}
		if (enableRet == UserServiceResultEnum.ENABLE_TOKEN_TIMEOUT) {
			return new HttpResult(HttpResultEnum.ENABLE_TOKEN_TIMEOUT);
		}
		return new HttpResult(HttpResultEnum.ENABLE_FAILED);
	}

	@PostMapping("signin")
	public UserSignInResult signIn(@RequestBody SignInUser signInUser) {
		UserSignInResult signin = userService.signin(signInUser);
		if (!Objects.equals(signin.getStatus(), UserServiceResultEnum.LOGIN_SUCCESS.name())) {
			signin.setStatus(UserServiceResultEnum.LOGIN_FAILED.name());
		}
		return signin;
	}

	@PostMapping("signout")
	public HttpResult signOut(@RequestBody AuthUser au) {
		UserServiceResultEnum result = userService.signOut(au);
		if (result == UserServiceResultEnum.SIGNOUT_SUCCESS) {
			return new HttpResult(HttpResultEnum.SIGN_OUT_SUCCESS);
		}
		return new HttpResult(HttpResultEnum.SIGN_OUT_FAILED);
	}
}
