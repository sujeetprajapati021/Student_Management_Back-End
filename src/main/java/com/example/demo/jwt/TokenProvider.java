package com.example.demo.jwt;


import static com.example.demo.jwt.JwtConstants.JWT_USER_KEY;
import static com.example.demo.jwt.JwtConstants.ROLE;

import java.util.*;
import java.util.function.Function;


import com.example.demo.enums.AdminType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenProvider {


    @Value("${jwt.secret}")
    private String jwtSecretSigningKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecretSigningKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(JwtUser jwtUser) {
        System.out.println("JWT USER : " + jwtUser.toString());
        return Jwts.builder()
                .setSubject(jwtUser.getUsername())
                .claim(ROLE, jwtUser.getAuthorities())
                .claim(JWT_USER_KEY, jwtUser)
                //              .claim(ROLE_NAME, jwtUser.getRoleName())
                .signWith(SignatureAlgorithm.HS256, jwtSecretSigningKey)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (
                username.equals(userDetails.getUsername())
                        && !isTokenExpired(token));
    }

    public JwtUser getJwtUser(final String token) {
        final JwtParser jwtParser = Jwts.parser().setSigningKey(jwtSecretSigningKey);
        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
        final Claims claims = claimsJws.getBody();

        System.out.println("SUBJECT->" + claims.getSubject());
        System.out.println("JWT USER->" + claims.get(JWT_USER_KEY));
        System.out.println("ROLE->" + claims.get(ROLE));

        ArrayList<LinkedHashMap<String, String>> roleList = (ArrayList<LinkedHashMap<String, String>>) claims.get(ROLE);
        LinkedHashMap<String, String> roleMap = roleList.get(0);
        String roleValue = roleMap.get("authority");
        Integer lookupId = AdminType.findLookupIdByName(roleValue);
        AdminType adminType = AdminType.findAdminTypeByLookupId(lookupId);

        JwtUser jwtUser = new JwtUser();
        jwtUser.setAdminType(adminType);
        jwtUser.setUsername(claims.getSubject());

//       jwtUser.setRoleId((String)claims.get(ROLE));
//        jwtUser.setRoleName((String)claims.get(ROLE_NAME));
        return jwtUser;
    }

}
