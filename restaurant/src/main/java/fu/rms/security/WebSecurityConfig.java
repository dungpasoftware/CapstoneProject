package fu.rms.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import fu.rms.security.handler.RestAccessDeniedHandler;
import fu.rms.security.handler.RestAuthenticationFailureHandler;
import fu.rms.security.handler.RestAuthenticationSuccessHandler;
import fu.rms.security.service.MyUserDetailService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyUserDetailService myUserDetailService;


	@Bean
	RestAccessDeniedHandler accessDeniedHandler() {
		return new RestAccessDeniedHandler();
	}

	@Bean
	RestAuthenticationEntryPoint authenticationEntryPoint() {
		return new RestAuthenticationEntryPoint();
	}

	@Bean
	RestAuthenticationFailureHandler authenticationFailureHandler() {
		return new RestAuthenticationFailureHandler();
	}

	@Bean
	RestAuthenticationSuccessHandler authenticationSuccessHandler() {
		return new RestAuthenticationSuccessHandler();
	}

	@Bean
	public JWTAuthenFilter jwtAuthenFilter() {
		return new JWTAuthenFilter();
	}

	@Bean
	public JWTLoginFilter jwtLoginFilter() throws Exception {
		JWTLoginFilter jwtLoginFilter = new JWTLoginFilter("/login", authenticationManager());
		jwtLoginFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
		jwtLoginFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
		return jwtLoginFilter;

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// configure for authorization
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable();
		http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
		http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/login").permitAll()
				.antMatchers(HttpMethod.POST, "/preLogin").permitAll()
				.antMatchers("/rms-websocket/**").permitAll()
//				.antMatchers("/manager/**").hasRole("MANAGER")
				.anyRequest().authenticated().and()
				.addFilterBefore(jwtLoginFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(jwtAuthenFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	// configure get user in database
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailService).passwordEncoder(passwordEncoder());
	}

	// configure for cors
	@SuppressWarnings("deprecation")
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("POST", "OPTIONS", "GET", "DELETE", "PUT"));
		configuration.setAllowedHeaders(Arrays.asList(CrossOrigin.DEFAULT_ALLOWED_HEADERS));
		configuration.setAllowCredentials(true);
		configuration.setMaxAge(3000L);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;

	}

}
