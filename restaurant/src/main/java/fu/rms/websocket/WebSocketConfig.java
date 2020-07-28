package fu.rms.websocket;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import fu.rms.security.JWTAuthenFilter;
import fu.rms.security.service.MyUserDetail;
import fu.rms.security.service.MyUserDetailService;
import fu.rms.utils.JWTUtils;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	@Autowired
	private MyUserDetailService myUserDetailService;

	private static final Logger logger = LoggerFactory.getLogger(JWTAuthenFilter.class);

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic");
		config.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/rms-websocket").setAllowedOrigins("*").withSockJS();
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(new ChannelInterceptor() {
			@Override
			public Message<?> preSend(Message<?> message, MessageChannel channel) {
				try {

					StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message,
							StompHeaderAccessor.class);
					if (StompCommand.CONNECT.equals(accessor.getCommand())) {

						List<String> tokenList = accessor.getNativeHeader("token");
						String token = tokenList.get(0);
						if (StringUtils.hasText(token) && JWTUtils.validateJwtToken(token)) {
							logger.info("Valid token");
							String username = JWTUtils.getUsernameOfJwtToken(token);
							MyUserDetail myUserDetail = myUserDetailService.loadUserByUsername(username);
							// check user exists
							if (myUserDetail != null) {
								UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
										myUserDetail, null, myUserDetail.getAuthorities());
								accessor.setUser(usernamePasswordAuthenticationToken);

							}
						}

					}
				} catch (Exception e) {
					logger.error(e.getMessage());
				}

				return message;
			}
		});

	}

}
