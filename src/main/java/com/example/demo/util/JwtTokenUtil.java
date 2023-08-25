package com.example.demo.util;
import com.example.demo.config.*;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.demo.model.Login;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component
public class JwtTokenUtil {


    @Value("${jwt.secret}")
    private String secret;


    @Value("${jwt.expiration}")
    private Long expiration;

    private String createToken(String Subject) {
    	return Jwts.builder()
    			.setSubject(Subject)
    			.setIssuedAt(new Date())
    			.setExpiration(new Date(System.currentTimeMillis()+expiration))
    			.signWith(SignatureAlgorithm.HS256,secret)
    			.compact();
    }
    
    public String generateToken(Optional <Login> loginOptional){
    	if(loginOptional.isPresent()) {
    		Login login = loginOptional.get();
    		return createToken(login.getUserid());
    	}
    	return null;
    }
    
    

//    @SuppressWarnings("deprecation")
//	public String generateToken(Optional<Login> user) {
//        return Jwts.builder()
//                .setSubject(user.getUsername())
//                .claim("authorities", userDetails.getAuthorities())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + expiration))
//                .signWith(SignatureAlgorithm.HS256, secret)
//                .compact();
//    }



    public List<GrantedAuthority> getAuthoritiesFromToken(String token) {
    	@SuppressWarnings("deprecation")
		final Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    
    // Assuming authorities are stored as a JSON array in the token claims
    	List<String> authorities = claims.get("authorities", List.class);
    
    	return authorities.stream()
    			.map(SimpleGrantedAuthority::new)
    			.collect(Collectors.toList());
    }



    
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }


    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }


    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        @SuppressWarnings("deprecation")
		final Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }
    

//	public String generateToken(Optional<Login> user) {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
