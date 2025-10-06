package com.junhee92kr.chat.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.junhee92kr.chat.dto.UserCreateRequestDto;
import com.junhee92kr.chat.dto.UserCreateResponseDto;
import com.junhee92kr.chat.dto.UserLoginRequestDto;
import com.junhee92kr.chat.dto.UserResponseDto;
import com.junhee92kr.chat.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    
    // User -> UserResponse
    UserResponseDto userToUserResponse(User user);
    
    UserCreateRequestDto userToUserCreateRequest(User user);
    
    UserLoginRequestDto userToUserLoginRequest(User user);
    
    User userCreateRequestToUser(UserCreateRequestDto dto);
    
    UserCreateResponseDto userCreateResponseDto(User user);
}
