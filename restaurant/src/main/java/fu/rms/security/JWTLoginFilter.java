package fu.rms.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import fu.rms.security.service.MyUserDetail;

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
		logger.info("Login Process");
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(phone, password, java.util.Collections.emptyList()));

	}

	// when login successfully
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		MyUserDetail myUserDetail = (MyUserDetail) authResult.getPrincipal();
		String token = JWTUtils.generateJwtToken(myUserDetail);
		List<GrantedAuthority> authorities=(List<GrantedAuthority>) myUserDetail.getAuthorities();	
		List<String> roles=authorities.stream().map((authority)-> authority.getAuthority()).collect(Collectors.toList());	
		MyJsonToken myJsonToken=new MyJsonToken(token, roles.get(0));
		String jsonString=JWTUtils.objectToJson(myJsonToken);	
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(jsonString);
		out.flush();
		logger.info("Login Successfully");

	}

	// when login failed
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		logger.info("Login Failed");
	}

}
