package com.junhee92kr.cafe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.junhee92kr.cafe.dto.MenuRequestDto;
import com.junhee92kr.cafe.dto.MenuResponseDto;
import com.junhee92kr.cafe.entity.MenuEntity;

@Mapper(componentModel = "spring")
public interface MenuMapper {
	MenuMapper INSTANCE = Mappers.getMapper(MenuMapper.class);

	MenuResponseDto entityToResDto(MenuEntity menuEntity);

	MenuEntity saveReqToEntity(MenuRequestDto menuDto);
}
