package hello.com.plantynet.service;

import org.springframework.stereotype.Service;

import hello.com.plantynet.domain.Account;
import hello.com.plantynet.repository.UserMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private final UserMapper userMapper;

	@Override
	public void save(Account account) {
		userMapper.save(account);
	}
}
