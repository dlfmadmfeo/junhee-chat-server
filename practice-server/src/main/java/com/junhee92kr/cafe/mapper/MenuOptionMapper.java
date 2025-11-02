package com.junhee92kr.cafe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.junhee92kr.cafe.dto.MenuOptionRequestDto;
import com.junhee92kr.cafe.dto.MenuOptionResponseDto;
import com.junhee92kr.cafe.entity.MenuOptionEntity;

@Mapper(componentModel = "spring")
public interface MenuOptionMapper {
	MenuOptionMapper INSTANCE = Mappers.getMapper(MenuOptionMapper.class);

	MenuOptionResponseDto entityToResDto(MenuOptionEntity entity);

	@Mapping(target = "createdDate", ignore = true)
	@Mapping(target = "updatedDate", ignore = true)
	MenuOptionEntity saveReqToEntity(MenuOptionRequestDto dto);
}
