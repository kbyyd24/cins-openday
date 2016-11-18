package cn.edu.swpu.cins.openday.controller;

import cn.edu.swpu.cins.openday.enums.ExceptionMsgEnum;
import cn.edu.swpu.cins.openday.enums.HttpResultEnum;
import cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum;
import cn.edu.swpu.cins.openday.exception.OpenDayException;
import cn.edu.swpu.cins.openday.model.http.HttpResult;
import cn.edu.swpu.cins.openday.model.http.SignInUser;
import cn.edu.swpu.cins.openday.model.http.SignUpUser;
import cn.edu.swpu.cins.openday.model.http.UserSignInResult;
import cn.edu.swpu.cins.openday.model.service.AuthUser;
import cn.edu.swpu.cins.openday.service.TimeService;
import cn.edu.swpu.cins.openday.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static cn.edu.swpu.cins.openday.enums.service.UserServiceResultEnum.ENABLE_TOKEN_SUCCESS;

@RestController
@RequestMapping("user")
public class UserController {
	private UserService userService;
	private TimeService timeService;

	@Autowired
	public UserController(UserService userService, TimeService timeService) {
		this.userService = userService;
		this.timeService = timeService;
	}

	@PostMapping("signup")
	public HttpResult signUp(@RequestBody SignUpUser signUpUser) {
		String token = String.valueOf(timeService.getCurrentTimeMillis());
		try {
			return userService.signUp(signUpUser, token);
		} catch (OpenDayException ode) {
			String message = ode.getMessage();
			if (message.equals(ExceptionMsgEnum.SEND_MAIL_FAILED.name())) {
				return new HttpResult(HttpResultEnum.SEND_MAIL_FAILED);
			}
			if (message.equals(ExceptionMsgEnum.SAVE_CACHE_FAILED.name())) {
				return new HttpResult(HttpResultEnum.SIGN_UP_USER_FAILED);
			}
			return new HttpResult(HttpResultEnum.UNKNOWN_ERROR);
		}
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
		return userService.signin(signInUser);
	}

	@PostMapping("signout")
	public HttpResult signOut(@RequestBody AuthUser au) {
		UserServiceResultEnum result = userService.signOut(au);
		if (result == UserServiceResultEnum.SIGN_OUT_SUCCESS) {
			return new HttpResult(HttpResultEnum.SIGN_OUT_SUCCESS);
		}
		return new HttpResult(HttpResultEnum.SIGN_OUT_FAILED);
	}

	@GetMapping("online")
	public Boolean online(@CookieValue("cookieEmailOne") String mail, @CookieValue("cookieToken")String token) {
		return userService.online(mail, token);
	}
}
