package fu.rms.security.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import fu.rms.respone.LoginRespone;
import fu.rms.security.service.MyUserDetail;
import fu.rms.utils.JWTUtils;

public class RestAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private static final Logger logger = LoggerFactory.getLogger(RestAuthenticationSuccessHandler.class);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		MyUserDetail myUserDetail = (MyUserDetail) authentication.getPrincipal();
		// get token
		String token = JWTUtils.generateJwtToken(myUserDetail);
		// get staffId
		Long staffId = myUserDetail.getId();
		// get code
		String staffCode = myUserDetail.getCode();
		// get role
		@SuppressWarnings("unchecked")
		List<GrantedAuthority> authorities = (List<GrantedAuthority>) myUserDetail.getAuthorities();
		List<String> roles = authorities.stream().map((authority) -> authority.getAuthority())
				.collect(Collectors.toList());
		// response json to client
		response.setStatus(HttpServletResponse.SC_OK);
		LoginRespone loginRespone = new LoginRespone(token, staffId, staffCode, roles.get(0));
		String jsonString=JWTUtils.convertObjectToJson(loginRespone);
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(jsonString);
		out.flush();
		
		logger.info("Login Successfully");
		

	}

}
