package fu.rms.security;

import java.io.BufferedReader;
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
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import fu.rms.request.LoginRequest;
import fu.rms.security.service.MyUserDetail;
import fu.rms.utils.JWTUtils;

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
		//if method isn't post then throw exception
		if (!request.getMethod().equals("POST")) {
	        throw new AuthenticationServiceException(
	                "Authentication method not supported: " + request.getMethod());
	    }
		
		logger.info("Login Process");
		
		//get information user from client
		LoginRequest loginRequest;
	    try {
	        BufferedReader reader = request.getReader();
	        StringBuffer sb = new StringBuffer();
	        String line = null;
	        while ((line = reader.readLine()) != null){
	            sb.append(line);
	        }
	        ObjectMapper mapper = new ObjectMapper();
	        loginRequest = mapper.readValue(sb.toString(), LoginRequest.class);
	    } catch (Exception ex) {
	        throw new AuthenticationServiceException("Unable to read login credentials.");
	    }
	    //authentication
		return getAuthenticationManager().authenticate(
		new UsernamePasswordAuthenticationToken(loginRequest.getPhone(), loginRequest.getPassword(), java.util.Collections.emptyList()));
		

	}

	// when login successfully
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		//get information user
		try {
			
			MyUserDetail myUserDetail = (MyUserDetail) authResult.getPrincipal();
			//get token
			String token = JWTUtils.generateJwtToken(myUserDetail);
			//get staffId
			Long staffId=myUserDetail.getId();
			//get code
			String staffCode=myUserDetail.getCode();
			//get role
			@SuppressWarnings("unchecked")
			List<GrantedAuthority> authorities=(List<GrantedAuthority>) myUserDetail.getAuthorities();
			List<String> roles=authorities.stream().map((authority)-> authority.getAuthority()).collect(Collectors.toList());
			//convert object to json
			MyJsonToken myJsonToken=new MyJsonToken(token,staffId,staffCode,roles.get(0));
			ObjectMapper objectMapper=new ObjectMapper();
			String jsonString=objectMapper.writeValueAsString(myJsonToken);		
			//response json to client
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(jsonString);
			out.flush();
			
		} catch (Exception e) {
			throw new AuthenticationServiceException("Not Parse object token to json.");
		}
		logger.info("Login Successfully");
		getSuccessHandler().onAuthenticationSuccess(request, response, authResult);

	}

	// when login failed
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		logger.info("Login Failed");
		getFailureHandler().onAuthenticationFailure(request, response, failed);
	}

}
