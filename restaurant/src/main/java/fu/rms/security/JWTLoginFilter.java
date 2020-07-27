package fu.rms.security;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import fu.rms.request.LoginRequest;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	private static final Logger logger = LoggerFactory.getLogger(JWTLoginFilter.class);

	public JWTLoginFilter(String url, AuthenticationManager auManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(auManager);
	}

	// when start login
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		// if method isn't post then throw exception
		logger.info("Login Process");
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		// get information user from client
		LoginRequest loginRequest;
		try {
			BufferedReader reader = request.getReader();
			StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			ObjectMapper mapper = new ObjectMapper();
			loginRequest = mapper.readValue(sb.toString(), LoginRequest.class);
		} catch (Exception ex) {
			throw new AuthenticationServiceException("Unable to read login credentials.");
		}
		// authentication
		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getPhone(),
				loginRequest.getPassword(), java.util.Collections.emptyList()));

	}

	// when login successfully
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		getSuccessHandler().onAuthenticationSuccess(request, response, authResult);

	}

	// when login failed
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		getFailureHandler().onAuthenticationFailure(request, response, failed);
	}

}
