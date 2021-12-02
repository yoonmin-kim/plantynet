package hello.com.plantynet.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("{noop}a").roles("USER");
		auth.inMemoryAuthentication().withUser("manager").password("{noop}a").roles("MANAGER");
		auth.inMemoryAuthentication().withUser("admin").password("{noop}a").roles("ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/api/**", "/user/**").permitAll()
			.antMatchers("/**/editList").access("hasRole('MANAGER') or hasRole('ADMIN')")
			.antMatchers("/**/deleteList").hasRole("ADMIN")
			.anyRequest().authenticated();

		http.formLogin()
			.loginPage("/user/login");

		http.exceptionHandling()
			.accessDeniedHandler((request, response, accessDeniedException) -> {
				String requestURI = request.getRequestURI();
				if (requestURI.indexOf("jsp") > 0) {
					response.sendRedirect("/jsp");
				} else {
					response.sendRedirect("/thymeleaf");
				}
			});
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
}
