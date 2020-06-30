package fu.rms.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import fu.rms.constant.SecurityConstant;
import fu.rms.dto.StaffDto;
import fu.rms.entity.Staff;
import fu.rms.service.impl.RoleService;
import fu.rms.service.impl.StaffService;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    public JWTAuthenticationFilter() {
    }
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	StaffService staffService;
	
	@Autowired
	RoleService roleService;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) req;
		String authToken = httpRequest.getHeader("authorization");
		
		if(authToken == null || authToken.isEmpty()) {
			chain.doFilter(req, res);
			return;
		}
		
		if(jwtService.validateTokenLogin(authToken)) {
			String phone = jwtService.getPhoneFromToken(authToken);
			StaffDto staff = staffService.findStaffByPhone(phone);
			if(staff != null) {
				boolean enabled = true;
				boolean accountNonExpired = true;
				boolean credentialsNonExpired = true;
				boolean accountNonBlocked = true;
				UserDetails userDetails = new User
						(phone, staff.getPassword(), enabled, accountNonExpired, 
								credentialsNonExpired, accountNonBlocked, getAuthorities());
				UsernamePasswordAuthenticationToken authentication = 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		chain.doFilter(req, res);
		
	}
	
//	private AuthenticationManager authenticationManager;
//
//    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest req,
//                                                HttpServletResponse res) throws AuthenticationException {
//        try {
//            Staff staff = new ObjectMapper()
//                    .readValue(req.getInputStream(), Staff.class);
//
//            return authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            staff.getPhone(),
//                            staff.getPassword(),
//                            new ArrayList<>())
//            );
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest req,
//                                            HttpServletResponse res,
//                                            FilterChain chain,
//                                            Authentication auth) throws IOException, ServletException {
//
//        String token = JWT.create()
//                .withSubject(((User) auth.getPrincipal()).getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_TIME))
//                .sign(Algorithm.HMAC512(SecurityConstant.SECRET.getBytes()));
//        res.addHeader(SecurityConstant.HEADER_STRING, SecurityConstant.TOKEN_PREFIX + token);
//    }
	
	private List<GrantedAuthority> getAuthorities(){
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (int i = 0; i < roleService.findAllRoles().size(); i++) {
			authorities.add(new SimpleGrantedAuthority(roleService.findAllRoles().get(i).getRoleName()));
		}
		return authorities;
	}

}
