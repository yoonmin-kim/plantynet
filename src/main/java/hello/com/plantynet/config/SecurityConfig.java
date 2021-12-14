package hello.com.plantynet.config;

import javax.servlet.http.HttpSession;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import hello.com.plantynet.repository.UserMapper;
import hello.com.plantynet.security.JwtRequestFilter;
import hello.com.plantynet.security.MyAuthenticationProvider;
import hello.com.plantynet.security.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserMapper userMapper;
	private final JwtRequestFilter jwtRequestFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService());
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

		http.authorizeRequests()
			.antMatchers("/api/**", "/user/**").permitAll()
			.antMatchers("/**/editList").access("hasRole('MANAGER') or hasRole('ADMIN')")
			.antMatchers("/**/deleteList").hasRole("ADMIN")
			.anyRequest().authenticated();

		http.formLogin()
			.loginPage("/user/login")
			.loginProcessingUrl("/login_proc")
			.successHandler((request, response, authentication) -> {
				log.info("authentication={}", authentication.getName());
				response.sendRedirect("/thymeleaf");
			});

		http.logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl("/user/login")
			.addLogoutHandler((request, response, authentication) -> {
				HttpSession session = request.getSession();
				session.invalidate();
			});

		http.exceptionHandling()
			.authenticationEntryPoint((request, response, authException) -> {
				log.info("로그인 인증에 실패하였습니다.");
				response.sendRedirect("/user/login");
			})
			.accessDeniedHandler((request, response, accessDeniedException) -> {
				String requestURI = request.getRequestURI();
				log.info("권한이 부족하여 실행하지 못하였습니다.");
				if (requestURI.indexOf("jsp") > 0) {
					response.sendRedirect("/jsp");
				} else {
					response.sendRedirect("/thymeleaf");
				}
			});
		http.csrf().disable();
	}

	/**
	 * js/css/image 파일 등 보안필터를 적용할 필요가 없는 리소스설정
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new MyUserDetailsService(userMapper, passwordEncoder());
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		return new MyAuthenticationProvider(userDetailsService(), passwordEncoder());
	}
}
