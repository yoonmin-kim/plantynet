package hello.com.plantynet.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hello.com.plantynet.domain.Account;
import hello.com.plantynet.repository.UserMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

	public static final String USER_SERIVE_EXCEPTION = "잘못된 사용자 정보를 입력하였습니다.";
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;

	public void save(Account account) {
		userMapper.save(account);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = userMapper.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다{" + username + "}"));

		List<GrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority(account.getRole()));

		AccountContext accountContext = new AccountContext(account, roles);

		return accountContext;
	}

	public Account login(Account account) {
		Account account1 = userMapper.findByUsername(account.getUsername())
			.orElseThrow(() -> new UsernameNotFoundException(USER_SERIVE_EXCEPTION));

		if (!passwordEncoder.matches(account.getPassword(), account1.getPassword())) {
			throw new BadCredentialsException(USER_SERIVE_EXCEPTION);
		}

		return account1;
	}

}
