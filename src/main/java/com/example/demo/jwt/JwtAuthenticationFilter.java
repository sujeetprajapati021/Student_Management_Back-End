package com.example.demo.jwt;



import java.io.IOException;
import java.util.Arrays;
import java.util.List;



import com.example.demo.enums.Status;
import com.example.demo.response.BaseResponse;
import com.example.demo.service.IUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private List<String> excludedUrls;

    private String tokenHeader="Authorization";

    @Autowired
    private IUserService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain
    ) throws IOException, ServletException {

        String username = null;

        excludedUrls = Arrays.asList(
                "/springfox-swagger-ui",
                "/swagger-resources",
                "/swagger-ui.html",
                "/v2/api-docs",
                "/api/auth",
                "/api/ping",
                "/api/addUser",
                "/api/student/add",
                "/api/file/upload",
                "/api/document/upload",
                "/actuator/health",
                "/image/**"
        );

        if (excludedUrls.stream().noneMatch(url->
                req.getRequestURL().toString().toLowerCase().contains(url.toLowerCase()))
                && !req.getMethod().equals("OPTIONS")) {
//			System.out.println("RequestURL : " + req.getRequestURL().toString().toLowerCase());
//			System.out.println("Method Type : " + req.getMethod().toString());
            String authToken = req.getHeader(this.tokenHeader);

            if(null == authToken) {
                logger.error("Token can not be left blank");
                prepareErrorResponse(res, "Token can not be left blank");
                return;
            }

            try {
                if(!authToken.startsWith("Bearer ")) {
                    logger.info("Token must be starts with Bearer");
                    prepareErrorResponse(res, "Token must be starts with Bearer");
                    return;
                } else {
                    authToken = authToken.substring(7);
                    username = jwtTokenUtil.getUsernameFromToken(authToken);
                }
            } catch (MalformedJwtException e) {
                logger.info("The given token is malformed. Please try with a valid token");
                prepareErrorResponse(res, "The given token is malformed. Please try with a valid token");
                return;
            } catch (SignatureException e) {
                logger.info("Token is not valid");
                prepareErrorResponse(res, "Token is not valid");
                return;
            } catch (ExpiredJwtException e) {
                logger.info("Your token is expired. Please try with new token");
                prepareErrorResponse(res, "Your token is expired. Please try with new token");
                return;
            } catch (IllegalArgumentException e) {
                logger.info("Invalid request token found");
                prepareErrorResponse(res, "Invalid request token found");
                return;
            }

            try {
                if (username != null) {
                    //JwtUser jwtUser = jwtTokenUtil.getJwtUser(authToken); // required if need roleId & roleName
                    JwtUser jwtUser = userService.loadUserByUsername(username);
                    if (!jwtTokenUtil.isTokenExpired(authToken)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                new UsernamePasswordAuthenticationToken(
                                        jwtUser,
                                        null,
                                        jwtUser.getAuthorities());
                        usernamePasswordAuthenticationToken.setDetails(
                                new WebAuthenticationDetailsSource()
                                        .buildDetails(req));
                        logger.info("authenticated user: " + username + ", setting security context");
                        SecurityContextHolder.getContext().
                                setAuthentication(usernamePasswordAuthenticationToken);
                    }
                } else {
                    logger.info("Invalid user");
                    prepareErrorResponse(res, "Invalid user");
                    return;
                }
            } catch (Exception e) {
                prepareErrorResponse(res, "Invalid user");
                e.printStackTrace();
                return;
            }
        }

        chain.doFilter(req, res);
    }

    private void prepareErrorResponse(
            HttpServletResponse response,
            String errorMessage
    ) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        BaseResponse<Object> body = BaseResponse.builder()
                .status(Status.FAILURE)
                .errorMsg(errorMessage)
                .build();

        response.getOutputStream().println(objectMapper.writeValueAsString(body));
    }
}