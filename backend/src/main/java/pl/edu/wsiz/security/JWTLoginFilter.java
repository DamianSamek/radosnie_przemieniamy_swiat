package pl.edu.wsiz.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import pl.edu.wsiz.service.UserService;


public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	private UserService userService;
	
	public JWTLoginFilter(String url, AuthenticationManager authManager, UserService userService) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
		this.userService = userService;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException, IOException {
		
		UserCredentials creds = new ObjectMapper().readValue(req.getInputStream(), UserCredentials.class);
		
	return getAuthenticationManager()
				.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword()));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		String userRole = null;
		for (GrantedAuthority authority : userDetails.getAuthorities()) {
			userRole = authority.getAuthority();
			if (!(userRole == null))
				break;
		}
		TokenAuthenticationService.addAuthentication(res, auth.getName(), userRole);

	
		String userJson = userService.toJson(auth.getName());
		res.getWriter().write(userJson);
		res.setContentType("application/json");

	}
}
