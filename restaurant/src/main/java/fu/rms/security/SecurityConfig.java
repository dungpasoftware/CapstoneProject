package fu.rms.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
	public JWTAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		JWTAuthenticationFilter jwtAuthen = new JWTAuthenticationFilter();
		jwtAuthen.setAuthenticationManager(authenticationManager());
		return jwtAuthen;
	}
	
	@Bean
	public RestAuthenticationEntryPoint restServiceEntryPoint() {
		return new RestAuthenticationEntryPoint();
	}
	
	@Bean
	public CustomAccessDeniedHandler customAccessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}
	
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
//		http.csrf().ignoringAntMatchers("/**");
//		http.authorizeRequests().antMatchers("/sign_in**").permitAll();
//		http.antMatcher("/**").httpBasic().authenticationEntryPoint(restServiceEntryPoint())
//			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//			.and().authorizeRequests()
//				.antMatchers(HttpMethod.GET, "/**").access("hasRole('MANAGER') "
//						+ "or hasRole('CHEF') "
//						+ "or hasRole('CASHIER') "
//						+ "or hasRole('ORDERTAKER')")
//				.antMatchers(HttpMethod.POST, "/**").access("hasRole('MANAGER') "
//						+ "or hasRole('CHEF') "
//						+ "or hasRole('CASHIER') "
//						+ "or hasRole('ORDERTAKER')")
//				.antMatchers(HttpMethod.DELETE, "/**").access("hasRole('MANAGER') "
//						+ "or hasRole('CHEF') "
//						+ "or hasRole('CASHIER') "
//						+ "or hasRole('ORDERTAKER')")
//				.antMatchers(HttpMethod.PUT, "/**").access("hasRole('MANAGER') "
//						+ "or hasRole('CHEF') "
//						+ "or hasRole('CASHIER') "
//						+ "or hasRole('ORDERTAKER')")
		http.cors().and().csrf().disable().authorizeRequests()
			.antMatchers(HttpMethod.POST, "/sign_in").permitAll()
			.anyRequest().authenticated()
			.and()
			.antMatcher("/**").httpBasic().authenticationEntryPoint(restServiceEntryPoint())
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
			.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
	}
}
