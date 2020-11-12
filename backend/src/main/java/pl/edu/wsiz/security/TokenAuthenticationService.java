package pl.edu.wsiz.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;

public class TokenAuthenticationService {
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";
	static final String SECRET = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";
	static final String AUTHORITY_KEY = "USER_AUTHORITY";

	public static void addAuthentication(HttpServletResponse res, String username, String userRole) throws IOException {
		Map<String, Object> authorityClaims = new HashMap<String, Object>();
		authorityClaims.put(AUTHORITY_KEY, userRole);

		String JWT = Jwts.builder().setClaims(authorityClaims).setSubject(username)
				.signWith(SignatureAlgorithm.HS512, TextCodec.BASE64.decode(SECRET)).compact();
		res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);

	}

	public static Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);

		if (token != null && token.toLowerCase().contains(TOKEN_PREFIX.toLowerCase())) {
			String user = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody()
					.getSubject();
			Claims tokenClaims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
					.getBody();

			Collection<? extends GrantedAuthority> authorities = Arrays
					.asList(tokenClaims.get(AUTHORITY_KEY).toString().split(",")).stream()
					.map(authority -> new SimpleGrantedAuthority(authority)).collect(Collectors.toList());

			return user != null ? new UsernamePasswordAuthenticationToken(user, null, authorities) : null;
		}
		return null;
	}

}
