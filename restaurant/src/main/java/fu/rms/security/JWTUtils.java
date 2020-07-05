package fu.rms.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JWTUtils {

	private static final Logger logger = LoggerFactory.getLogger(JWTUtils.class);

	public static String generateJwtToken(UserDetails userDetails) {
		String token = Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 10000 * 60 * 60 * 24))
				.signWith(SignatureAlgorithm.HS512, "KeyBiMat").claim("role", userDetails.getAuthorities()).compact();
		return token;
	}

	public static String getUsernameOfJwtToken(String token) {
		return Jwts.parser().setSigningKey("KeyBiMat").parseClaimsJws(token).getBody().getSubject();
	}

	public static boolean validateJwtToken(String token) {
		try {
			Jwts.parser().setSigningKey("KeyBiMat").parseClaimsJws(token).getBody();
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature. ", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token.", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired. }", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported. ", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty. ", e.getMessage());
		}
		return false;
	}
	
	public static String objectToJson(MyJsonToken myJsonToken) {
		try {
			ObjectMapper objectMapper=new ObjectMapper();
			String jsonStr=objectMapper.writeValueAsString(myJsonToken);
			return jsonStr;
		} catch (JsonProcessingException e) {
			logger.error("Not Parse object token to json", e.getMessage());
		}
		return null;
	}

}
