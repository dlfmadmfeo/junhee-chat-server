package com.junhee92kr.chat.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.junhee92kr.chat.dto.TokenDto;
import com.junhee92kr.chat.dto.UserCreateRequestDto;
import com.junhee92kr.chat.dto.UserCreateResponseDto;
import com.junhee92kr.chat.dto.UserLoginRequestDto;
import com.junhee92kr.chat.dto.UserResponseDto;
import com.junhee92kr.chat.entity.User;
import com.junhee92kr.chat.mapper.UserMapper;
import com.junhee92kr.chat.repository.RefreshTokenRepository;
import com.junhee92kr.chat.repository.UserRepository;
import com.junhee92kr.chat.utils.JwtTokenProvider;
import com.junhee92kr.chat.utils.PasswordUtils;
import com.junhee92kr.common.code.ErrorCode;
import com.junhee92kr.common.exception.BaseException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	public List<UserResponseDto> getUserList() {
		List<User> userList = userRepository.findAll();
		return userList.stream().map(UserMapper.INSTANCE::userToUserResponse).collect(Collectors.toList());
	}
	
	public UserResponseDto getUserById(Long id) {
		Optional<User> userOptional = userRepository.findById(id);
		User user = userOptional.isPresent() ? userOptional.get() : null; 
		return UserMapper.INSTANCE.userToUserResponse(user);
	}
	
	public UserResponseDto getUserByEmail(String email) {
		Optional<User> userOptional = userRepository.findByEmail(email);
		User user = userOptional.isPresent() ? userOptional.get() : null;
		return UserMapper.INSTANCE.userToUserResponse(user);
	}
	
	@Transactional
	public UserCreateResponseDto saveUser(UserCreateRequestDto userRequest) {
		UserResponseDto userResposne = this.getUserByEmail(userRequest.getEmail());
		if (userResposne != null) {
			throw new BaseException(ErrorCode.USER_DUPLICATED);
		}
		String encodedPassword = PasswordUtils.encodePassword(userRequest.getPassword());
		userRequest.setPassword(encodedPassword);
		User user = UserMapper.INSTANCE.userCreateRequestToUser(userRequest);
		UserCreateResponseDto userCreateResponse = UserMapper.INSTANCE.userCreateResponseDto(user);
		userRepository.save(user);
		return userCreateResponse;
	}
	
	@Transactional
	public TokenDto login(UserLoginRequestDto loginRequest) {
		Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
		User user = userOptional.isPresent() ? userOptional.get() : null;
		if (user == null) {
			throw new BaseException(ErrorCode.USER_NOT_FOUND);
		}
		boolean matched = PasswordUtils.matches(loginRequest.getPassword(), user.getPassword()); 
		if (!matched) {
			throw new BaseException(ErrorCode.USER_PASSWORD_INCORRECT);
		}
		// 로그인 검증이 통과하면 토큰 반환
		// refreshToken DB 저장
		Long userId = user.getId();
		String strUserId = userId.toString();
		LocalDateTime expiryDate = LocalDateTime.now().plusDays(7);
		
	    String accessToken = jwtTokenProvider.createAccessToken(strUserId);
	    String refreshToken = jwtTokenProvider.createRefreshToken(strUserId);
	    
	    tokenService.saveRefreshToken(userId, refreshToken, expiryDate);
	    
	    return new TokenDto(accessToken, refreshToken, expiryDate);	
	}
	
	public String getCookieValue(HttpServletRequest httpRequest, TokenDto tokenResponseDto) {
		Instant instant = tokenResponseDto.getExpiryDate().atZone(ZoneId.systemDefault()).toInstant();
		long cookieMaxAge = instant.getEpochSecond() - Instant.now().getEpochSecond();
		
		String origin = httpRequest.getHeader("Origin");
	    boolean isLocal = origin != null && origin.contains("localhost");
	    String cookieValue;
		
	    if (isLocal) {
	    	// Secure 제거
	        cookieValue = String.format("access-token=%s; Max-Age=%d; Path=/; SameSite=Lax", tokenResponseDto.getAccessToken(), cookieMaxAge);
	    } else {
	        cookieValue = String.format("access-token=%s; Max-Age=%d; Path=/; Domain=junhee92kr.com; SameSite=None; Secure; HttpOnly", tokenResponseDto.getAccessToken(), cookieMaxAge);
	    }
	    
	    log.info("[getCookieValue]");
	    log.info("origin: {}", origin);
	    log.info("cookieValue: {}", cookieValue);
	    
		return cookieValue;
	}

}
