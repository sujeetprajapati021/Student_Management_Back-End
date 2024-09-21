package com.example.demo.config;

import com.example.demo.exception.AccessDeniedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class SecurityInterceptor implements HandlerInterceptor {

    @Value("${api.key}")
    private String APIKey;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //  String reqAPIKey = request.getHeader("x-api-key");
        String reqAPIKey = request.getHeader("x-api-key");

        // Define excluded URLs where the API key is not required
        List<String> excludedUrls = Arrays.asList(
                "/swagger-ui.html",
                "/swagger-ui/index.html",  // Ensure the index.html for Swagger is excluded
                "/v2/api-docs",
                "/swagger-resources/**",
                "/webjars/**"
        );

        // Skip API key validation for the excluded URLs
        String requestURI = request.getRequestURI().toLowerCase();
        boolean isExcludedUrl = excludedUrls.stream().anyMatch(requestURI::contains);

        if (isExcludedUrl) {
            return true; // Skip API key validation for Swagger endpoints
        }

        log.info("api-key received in request :: {}", reqAPIKey);
        if (!Objects.equals(APIKey, reqAPIKey)) {
            log.warn("Request received without api-key");
            throw new AccessDeniedException("Unauthorised request, Missing api-key in header");
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);


//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response,
//                           Object handler, ModelAndView modelAndView) throws Exception {
//
//        System.out.println("Post Handle method is Calling");
//    }
//    @Override
//    public void afterCompletion
//            (HttpServletRequest request, HttpServletResponse response, Object
//                    handler, Exception exception) throws Exception {
//
//        System.out.println("Request and Response is completed");
//    }

    }
}
