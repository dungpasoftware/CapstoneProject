package fu.rms.security;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import fu.rms.constant.SecurityConstant;

@Service
public class JWTService {

	public String generateTokenLogin(String phone) {
		String token = null;
		try {
			JWSSigner signer = new MACSigner(generateShareSecret());
			JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
			builder.claim(SecurityConstant.TOKEN_PREFIX, phone);
			builder.expirationTime(generateExpirationDate());
			
			JWTClaimsSet claimsSet = builder.build();
			SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
			
			signedJWT.sign(signer);
			
			token = signedJWT.serialize();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return token;
	}
	
	
	//get phone dang nhap
	public String getPhoneFromToken(String token) {
		String phone = null;
		try {
			JWTClaimsSet claims = getClaimsFromToken(token);
			phone = claims.getStringClaim(SecurityConstant.TOKEN_PREFIX);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return phone;
	}
	
	private JWTClaimsSet getClaimsFromToken(String token) {
		JWTClaimsSet claims = null;
		try {
			SignedJWT signedJWT = SignedJWT.parse(token);
			JWSVerifier verifier = new MACVerifier(generateShareSecret());
			if(signedJWT.verify(verifier)) {
				claims = signedJWT.getJWTClaimsSet();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return claims;
	}
	
	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_TIME);
	}
	
	private Date getExpirationDateFromToken(String token) {
		Date expiration = null;
		try {
			JWTClaimsSet claims = getClaimsFromToken(token);
			expiration = claims.getExpirationTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return expiration;
	}
	
	private byte[] generateShareSecret() {
		byte[] sharedSecret = new byte[32];
		sharedSecret = SecurityConstant.SECRET.getBytes();
		return sharedSecret;
	}
	
	private boolean isTokenExpired(String token) {
		Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	public boolean validateTokenLogin(String token) {
		if(token == null || token.trim().length() == 0) {
			return false;
		}
		String phone = getPhoneFromToken(token);
		if(phone == null || phone.isEmpty()) {
			return false;
		}
		if(isTokenExpired(token)) {
			return false;
		}
		return true;
	}
	
	
}
