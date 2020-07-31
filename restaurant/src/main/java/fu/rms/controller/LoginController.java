package fu.rms.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import fu.rms.respone.LoginRespone;
import fu.rms.security.service.MyUserDetail;
import fu.rms.security.service.MyUserDetailService;
import fu.rms.utils.JWTUtils;

@RestController
public class LoginController {

	@Autowired
	private MyUserDetailService myUserDetailService;

	@PostMapping("/pre-login")
	public ResponseEntity<LoginRespone> preLogin(HttpServletRequest request) {
		try {
			// check valid token
			String token=request.getHeader("token");
			if (StringUtils.hasText(token) && JWTUtils.validateJwtToken(token)) {
				String username = JWTUtils.getUsernameOfJwtToken(token);
				MyUserDetail myUserDetail = myUserDetailService.loadUserByUsername(username);
				// check user exists
				if (myUserDetail != null) {
					// set authentication
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							myUserDetail, null, myUserDetail.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					// set to object json to return client
					Long staffId = myUserDetail.getId();
					String staffCode = myUserDetail.getCode();
					List<String> roles = myUserDetail.getAuthorities().stream()
							.map((authority) -> authority.getAuthority()).collect(Collectors.toList());
					LoginRespone loginRespone = new LoginRespone(token, staffId, staffCode, roles.get(0));
					return new ResponseEntity<LoginRespone>(loginRespone, HttpStatus.OK);

				}
			}
		} catch (Exception e) {
			return new ResponseEntity<LoginRespone>(HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<LoginRespone>(HttpStatus.UNAUTHORIZED);
	}
}
