package fu.rms.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import fu.rms.advice.MessageError;
import fu.rms.security.service.MyUserDetail;
import fu.rms.security.service.MyUserDetailService;
import fu.rms.utils.JWTUtils;

public class JWTAuthenFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(JWTAuthenFilter.class);

	@Autowired
	private MyUserDetailService myUserDetailService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			// get token from client
			HttpServletRequest rq = (HttpServletRequest) request;
			String token = rq.getHeader("token");
			// check valid token
			if (StringUtils.hasText(token) && JWTUtils.validateJwtToken(token)) {
				String username = JWTUtils.getUsernameOfJwtToken(token);
				MyUserDetail myUserDetail = myUserDetailService.loadUserByUsername(username);
				// check user exists
				if (myUserDetail != null && myUserDetail.isEnabled()) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							myUserDetail, null, myUserDetail.getAuthorities());
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
			filterChain.doFilter(request, response);

		} catch (UsernameNotFoundException e) {
			logger.error(e.getMessage());
			String message = e.getMessage();
			MessageError messageError = new MessageError(HttpStatus.UNAUTHORIZED, message, "Unauthorized error");

			// response messageError to client
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			String jsonString = JWTUtils.convertObjectToJson(messageError);

			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(jsonString);
			out.flush();
		} catch (Exception e) {
			logger.error(e.getMessage());

		}

	}
}
