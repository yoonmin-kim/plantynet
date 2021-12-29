package hello.com.plantynet.controller;

import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hello.com.plantynet.domain.Account;
import hello.com.plantynet.domain.dto.AccountDto;
import hello.com.plantynet.security.MyUserDetailsService;
import hello.com.plantynet.service.utils.ResourceUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

	private final MyUserDetailsService userService;
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;
	private final ResourceUtil resourceUtil;

	@ModelAttribute("roles")
	public Map<String, String> roles() {
		return resourceUtil.getRoles();
	}

	@GetMapping("/login")
	public String loginForm() {
		return "thymeleaf/login/loginForm";
	}

	@GetMapping("/register")
	public String registerForm() {
		return "thymeleaf/login/registerForm";
	}

	@PostMapping("/register")
	public String register(AccountDto accountDto) {
		Account account = modelMapper.map(accountDto, Account.class);
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		userService.save(account);
		return "redirect:/user/login";
	}
}
