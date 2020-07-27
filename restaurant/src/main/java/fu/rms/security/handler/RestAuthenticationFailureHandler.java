package fu.rms.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import fu.rms.advice.MessageError;
import fu.rms.utils.JWTUtils;

public class RestAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private static final Logger logger = LoggerFactory.getLogger(RestAuthenticationFailureHandler.class);
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {		
		logger.error("Login Failed: "+exception.getMessage());
		
		//set messageError
		String message="Tài khoản hoặc mật khẩu không hợp lệ";
		MessageError messageError=new MessageError(HttpStatus.UNAUTHORIZED,message,"Unauthorized error");

		//response messageError to client
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		String jsonString=JWTUtils.convertObjectToJson(messageError);
       
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(jsonString);
		out.flush();
		
	}

}
