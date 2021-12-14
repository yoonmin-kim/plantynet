package hello.com.plantynet.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MyAuthenticationProvider implements AuthenticationProvider {

	public static final String BAD_CREDENTIALS_MESSAGE = "잘못된 사용자 정보입니다.";
	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(
		Authentication authentication) throws AuthenticationException {

		// 사용자가 입력한 정보
		String username = authentication.getName();
		String password = (String)authentication.getCredentials();

		AccountContext accountContext = (AccountContext)userDetailsService.loadUserByUsername(username);
		if (!passwordEncoder.matches(password, accountContext.getPassword())) {
			throw new BadCredentialsException(BAD_CREDENTIALS_MESSAGE);
		}

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
			accountContext.getAccount(), null, accountContext.getAuthorities());

		return authenticationToken;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
