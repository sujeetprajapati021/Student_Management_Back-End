package com.example.demo.jwt;

public class JwtConstants {

    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5*60*60;
//	 public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 60;
    public static final String SIGNING_KEY = "devglan123r";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String JWT_USER_KEY = "jwtUser";
    public static final String ROLE = "role";
    public static final String ROLE_NAME = "roleName";
}
