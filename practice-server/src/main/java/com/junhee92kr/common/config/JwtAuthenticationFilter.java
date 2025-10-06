package com.junhee92kr.common.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junhee92kr.chat.dto.UserResponseDto;
import com.junhee92kr.chat.service.UserService;
import com.junhee92kr.chat.utils.JwtTokenProvider;
import com.junhee92kr.common.code.ErrorCode;
import com.junhee92kr.common.exception.BaseException;
import com.junhee92kr.common.response.ApiResponse;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    
    private final UserService userService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
    	
    	String uri = request.getRequestURI();
    	String method = request.getMethod();
    	
    	log.info("uri: {}", uri);
    	log.info("method: {}", method);
    	
        // 모든 OPTIONS 요청은 필터 통과
        if ("OPTIONS".equalsIgnoreCase(method)) {
            filterChain.doFilter(request, response);
            return;
        }
    	
    	boolean isSaveUserUrl = uri.equals("/user") && (method.equalsIgnoreCase("post"));
    	boolean isLoginUserUrl = uri.equals("/user/login") && (method.equalsIgnoreCase("post"));
    	boolean isSocketUrl = uri.startsWith("/ws");
    	// ✅ Swagger, API Docs 요청은 토큰 검증 제외
    	boolean isSwaggerUrl = uri.startsWith("/swagger-ui") || uri.startsWith("/v3/api-docs") || uri.startsWith("/swagger-resources");
    	
    	if (isSaveUserUrl || isLoginUserUrl || isSocketUrl || isSwaggerUrl) {
    		filterChain.doFilter(request, response);
    		return;
    	}
    	
    	try {
    		String origin = request.getHeader("host");
//    		boolean isLocal = origin != null && origin.contains("localhost");
//    		boolean isLocal = request.getHeader("X-Local-Request").equals("true") ? true : false;
    		String localHeader = request.getHeader("X-Local-Request");
    		boolean isLocal = origin.contains("localhost") || "true".equalsIgnoreCase(localHeader);

	        String token = resolveToken(request);
	        
	        log.info("request: {}", request);
	        log.info("header: {}", request.getHeader("X-Local-Request"));
	        log.info("origin: {}", origin);
	        log.info("isLocal: {}", isLocal);
	        log.info("token: {}", token);
	        
	        if (!isLocal) {
		        if (token == null) {
		        	throw new BaseException(ErrorCode.TOKEN_NOT_FOUND);
		        }
		        if (!jwtTokenProvider.validateToken(token)) {
		        	throw new BaseException(ErrorCode.TOKEN_INVALID);
		        }
	            String userId = jwtTokenProvider.getUserId(token);
	            log.info("userId: {}", userId);
	            UserResponseDto userResponseDto = userService.getUserById(Long.valueOf(userId));
	            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userResponseDto, null, new ArrayList<>());
	        	SecurityContextHolder.getContext().setAuthentication(authentication);
	        }	
	        filterChain.doFilter(request, response);
    	} catch (BaseException ex) {
    		handleException(response, ex);
    	}
    }
    
    private String resolveToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
        	for(Cookie cookie : cookies) {
        		log.info("cookie: {}", cookie.getName());
        		if (cookie.getName().equals("access-token")) {
        			return cookie.getValue();
        		}
        	}
        }        
        return null;
    }

// Authorization Bearer 방식. Client에서 Request Header에 Authorization 방식으로 넘겨줘야 함
//    private String resolveToken(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
    
    private void handleException(HttpServletResponse response, BaseException ex) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        ApiResponse<Void> apiResponse = ApiResponse.fail(ex.getErrorCode().getCode(), ex.getMessage());
        String json = new ObjectMapper().writeValueAsString(apiResponse);

        response.getWriter().write(json);
    }
}
