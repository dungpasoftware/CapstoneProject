package fu.rms.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import fu.rms.security.service.MyUserDetailService;

public class JWTAuthenFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(JWTAuthenFilter.class);

	@Autowired
	private MyUserDetailService myUserDetailService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			//get token from client
			HttpServletRequest rq = (HttpServletRequest) request;
			String token = rq.getHeader("token");
			//check valid token
			if (StringUtils.hasText(token) && JWTUtils.validateJwtToken(token)) {
				String username = JWTUtils.getUsernameOfJwtToken(token);
				UserDetails userDetails = myUserDetailService.loadUserByUsername(username);
				//check user exists
				if (userDetails != null) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					logger.info("Valid Token");
					filterChain.doFilter(request, response);
				}
			}else {
				logger.info("Invalid Token");
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("Cannot set user authentication ", e);
		}

	}

}
