package hello.com.plantynet.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hello.com.plantynet.domain.Account;
import hello.com.plantynet.domain.DeliveryCode;
import hello.com.plantynet.domain.Item;
import hello.com.plantynet.domain.ItemType;
import hello.com.plantynet.domain.dto.AccountDto;
import hello.com.plantynet.domain.dto.ItemDto;
import hello.com.plantynet.domain.dto.ResponseDto;
import hello.com.plantynet.security.MyUserDetailsService;
import hello.com.plantynet.security.utils.CookieUtil;
import hello.com.plantynet.security.utils.JwtUtil;
import hello.com.plantynet.service.ItemService;
import hello.com.plantynet.service.utils.RedisUtil;
import hello.com.plantynet.service.utils.ResourceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReactController {

	public static final String GLOBAL_ERROR_CODE = "required";
	public static final String DEFAULT_GLOBAL_MESSAGE = "가격 * 수량 을 250,000 이상 맞춰주세요";
	private final ItemService itemService;
	private final MyUserDetailsService userService;
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper modelMapper;
	private final JwtUtil jwtUtil;
	private final CookieUtil cookieUtil;
	private final RedisUtil redisUtil;
	private final ResourceUtil resourceUtil;

	@GetMapping("/deliveryCodes")
	public List<DeliveryCode> deliveryCodes() {
		return resourceUtil.getDeliveryCodes();
	}

	@GetMapping("/regions")
	public Map<String, String> regions() {
		return resourceUtil.getRegions();
	}

	@GetMapping("/itemTypes")
	public Map<ItemType, String> itemType() {
		return resourceUtil.getItemType();
	}

	@GetMapping("/roles")
	public Map<String, String> roles() {
		return resourceUtil.getRoles();
	}

	@PostMapping("/item")
	public Object save(@Validated @RequestBody ItemDto itemDto, BindingResult bindingResult) {
		if (itemDto.isGlobalError()) {
			bindingResult.reject(GLOBAL_ERROR_CODE, DEFAULT_GLOBAL_MESSAGE);
		}
		if (bindingResult.hasErrors()) {
			log.info("errors = {}", bindingResult);
			return bindingResult.getAllErrors();
		}

		return new ResponseEntity(itemService.save(modelMapper.map(itemDto, Item.class)), HttpStatus.CREATED);
	}

	@GetMapping("/list")
	public List<Item> list() {
		return itemService.findAll();
	}

	@GetMapping("/editForm/{itemId}")
	public Item editForm(@PathVariable Long itemId) {
		Item findItem = itemService.findById(itemId);
		return findItem;
	}

	@PutMapping("/editForm")
	public Object edit(@Validated @RequestBody ItemDto itemDto, BindingResult bindingResult) {
		if (itemDto.isGlobalError()) {
			bindingResult.reject(GLOBAL_ERROR_CODE, DEFAULT_GLOBAL_MESSAGE);
		}
		if (bindingResult.hasErrors()) {
			log.info("errors = {}", bindingResult);
			return bindingResult.getAllErrors();
		}
		itemService.update(itemDto.getId(), modelMapper.map(itemDto, Item.class));
		return new ResponseEntity(HttpStatus.CREATED);
	}

	@DeleteMapping("/deleteList/{itemId}")
	public ResponseEntity delete(@PathVariable Long itemId) {
		itemService.deleteById(itemId);
		List<Item> list = list();
		return new ResponseEntity(list, HttpStatus.OK);
	}

	@GetMapping("/authorize/{authority}")
	public ResponseDto authorize(@PathVariable String authority) {
		String[] authorityArr = authority.split(",");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			String auth = grantedAuthority.getAuthority().toLowerCase();
			for(String a : authorityArr){
				if (auth.contains(a)) {
					return new ResponseDto("success", "권한승인 되었습니다.", null);
				}
			}
		}
		return new ResponseDto("error", "권한이 부족합니다.", null);
	}

	@PostMapping("/login")
	public ResponseDto login(@RequestBody AccountDto accountDto, HttpServletResponse res) {
		try {
			final Account account = userService.login(modelMapper.map(accountDto, Account.class));
			final String token = jwtUtil.generateToken(account);
			final String refreshJwt = jwtUtil.generateRefreshToken(account);
			Cookie accessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, token);
			Cookie refreshToken = cookieUtil.createCookie(JwtUtil.REFRESH_TOKEN_NAME, refreshJwt);
			redisUtil.setDataExpire(refreshJwt, account.getUsername(), JwtUtil.REFRESH_TOKEN_VALIDATION_SECOND);
			res.addCookie(accessToken);
			res.addCookie(refreshToken);
			return new ResponseDto("success", "로그인에 성공했습니다.", token);
		} catch (Exception e) {
			return new ResponseDto("error", "로그인에 실패했습니다.", e.getMessage());
		}
	}

	@GetMapping("/logout")
	public ResponseDto logout(HttpServletRequest req, HttpServletResponse res) {
		Cookie accessToken = cookieUtil.getCookie(req, "accessToken");
		Cookie refreshToken = cookieUtil.getCookie(req, "refreshToken");

		redisUtil.deleteData(refreshToken.getValue());
		cookieUtil.deleteCookie(accessToken);
		cookieUtil.deleteCookie(refreshToken);
		res.addCookie(accessToken);
		res.addCookie(refreshToken);
		return new ResponseDto("success", "로그아웃 되었습니다.", null);
	}

	@PostMapping("/register")
	public ResponseDto register(@RequestBody AccountDto accountDto) {
		Account account = modelMapper.map(accountDto, Account.class);
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		userService.save(account);
		return new ResponseDto("success", "회원가입이 완료되었습니다.", null);
	}
}
