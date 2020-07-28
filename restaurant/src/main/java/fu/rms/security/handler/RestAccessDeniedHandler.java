package fu.rms.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import fu.rms.advice.MessageError;
import fu.rms.utils.JWTUtils;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(RestAccessDeniedHandler.class);

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		logger.error("Access Denied: "+accessDeniedException.getMessage());
		
		//set messageError
		String message="Tài khoản của bạn không được phép truy cập";
		MessageError messageError=new MessageError(HttpStatus.FORBIDDEN,message,"Access Denied");

		//response messageError to client
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		String jsonString=JWTUtils.convertObjectToJson(messageError);
        
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(jsonString);
		out.flush();

	}

}
