package fu.rms.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import fu.rms.constant.SecurityConstant;
import fu.rms.entity.Staff;
import fu.rms.service.impl.StaffService;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	
//	@Autowired
//	private JWTService jwtService;
//	
//	@Autowired
//	StaffService staffService;
//	
//	@Override
//	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
//			throws IOException, ServletException {
//		
//		HttpServletRequest httpRequest = (HttpServletRequest) req;
//		String authToken = httpRequest.getHeader(SecurityConstant.HEADER_STRING);
//		
//		if(jwtService.validateTokenLogin(authToken)) {
//			String email = jwtService.getEmailFromToken(authToken);
//			Staff staff = staffService.findStaffByEmail(email);
//			if(staff != null) {
//				boolean enabled = true;
//				boolean accountNonExpired = true;
//				boolean credentialsNonExpired = true;
//				boolean accountNonBlocked = true;
//				UserDetails userDetails = new User
//						(email, staff.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonBlocked, )
//			}
//		}
//		
//	}
	
	
	
	
	
	

//	private AuthenticationManager authenManager;
//
//	public JWTAuthenticationFilter(AuthenticationManager authenManager) {
//		this.authenManager = authenManager;
//	}
//	// qua trinh xac thuc
//	@Override
//	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
//			throws AuthenticationException {
//		
//		try {
//			Staff staff = new ObjectMapper().readValue(request.getInputStream(), Staff.class);
//			
//			return authenManager.authenticate(
//					new UsernamePasswordAuthenticationToken(
//							staff.getEmail(), 
//							staff.getPassword(),
//							new ArrayList<>())
//					);
//			
//		} catch (Exception e) {
//			throw new RuntimeException();
//		}
//	
//	}
//	//tao ma token
//	@Override
//	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
//			Authentication authResult) throws IOException, ServletException {
//		
//		String token = JWT.create()
//				.withSubject(((User) authResult.getPrincipal()).getUsername())
//				.withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_TIME))
//				.sign(Algorithm.HMAC512(SecurityConstant.SECRET.getBytes()));
//		
//		response.addHeader(SecurityConstant.HEADER_STRING, SecurityConstant.TOKEN_PREFIX + token);
//	}
	
	
}
