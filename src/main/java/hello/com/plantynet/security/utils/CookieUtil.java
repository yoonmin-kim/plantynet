package hello.com.plantynet.security.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

@Service
public class CookieUtil {
	public Cookie createCookie(String cookieName, String value) {
		Cookie token = new Cookie(cookieName, value);
		token.setHttpOnly(true);
		token.setMaxAge((int)JwtUtil.TOKEN_VALIDATION_SECOND);
		token.setPath("/");
		return token;
	}

	public Cookie deleteCookie(Cookie token) {
		token.setValue("");
		token.setMaxAge(0);
		token.setPath("/");
		return token;
	}

	public Cookie getCookie(HttpServletRequest req, String cookieName) {
		final Cookie[] cookies = req.getCookies();
		if (cookies == null)
			return null;
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(cookieName))
				return cookie;
		}
		return null;
	}
}
