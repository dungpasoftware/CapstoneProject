package fu.rms.security.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		Map<String, Object> mapResponse = new HashMap<>();
		mapResponse.put("status", "34");
		mapResponse.put("message", "unauthorized api access");

		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		OutputStream out = response.getOutputStream();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writerWithDefaultPrettyPrinter().writeValue(out, response);
		out.flush();

	}

}
