package hello.com.plantynet.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import hello.com.plantynet.domain.Account;
import hello.com.plantynet.security.utils.CookieUtil;
import hello.com.plantynet.security.utils.JwtUtil;
import hello.com.plantynet.service.utils.RedisUtil;
import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private MyUserDetailsService userDetailsService;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private CookieUtil cookieUtil;
	@Autowired
	private RedisUtil redisUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain chain) throws
		ServletException, IOException {

		final Cookie jwtToken = cookieUtil.getCookie(request, JwtUtil.ACCESS_TOKEN_NAME);

		String username = null;
		String jwt = null;
		String refreshJwt = null;
		String refreshUname = null;

		try {
			if (jwtToken != null) {
				jwt = jwtToken.getValue();
				username = jwtUtil.getUsername(jwt);
			}
			if (username != null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);

				if (jwtUtil.validateToken(jwt, userDetails)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken.setDetails(
						new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
		} catch (ExpiredJwtException e) {
			Cookie refreshToken = cookieUtil.getCookie(request, JwtUtil.REFRESH_TOKEN_NAME);
			if (refreshToken != null) {
				refreshJwt = refreshToken.getValue();
			}
		} catch (Exception e) {

		}

		try {
			if (refreshJwt != null) {
				refreshUname = redisUtil.getData(refreshJwt);

				if (refreshUname.equals(jwtUtil.getUsername(refreshJwt))) {
					UserDetails userDetails = userDetailsService.loadUserByUsername(refreshUname);
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken.setDetails(
						new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

					Account account = new Account();
					account.setUsername(refreshUname);
					String newToken = jwtUtil.generateToken(account);

					Cookie newAccessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, newToken);
					response.addCookie(newAccessToken);
				}
			}
		} catch (ExpiredJwtException e) {

		}

		chain.doFilter(request, response);
	}
}
