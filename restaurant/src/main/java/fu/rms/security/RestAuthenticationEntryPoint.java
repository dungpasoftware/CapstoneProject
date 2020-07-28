package fu.rms.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import fu.rms.advice.MessageError;
import fu.rms.utils.JWTUtils;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint{
	
	private static final Logger logger = LoggerFactory.getLogger(RestAuthenticationEntryPoint.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		logger.error("Unauthorized error: "+authException.getMessage());
	   //set messageError
		String message="Lỗi đăng nhập";
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
