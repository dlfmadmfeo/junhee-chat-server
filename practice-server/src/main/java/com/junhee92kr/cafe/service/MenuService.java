package com.junhee92kr.cafe.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.junhee92kr.cafe.dto.MenuRequestDto;
import com.junhee92kr.cafe.dto.MenuResponseDto;
import com.junhee92kr.cafe.entity.MenuEntity;
import com.junhee92kr.cafe.mapper.MenuMapper;
import com.junhee92kr.cafe.repository.MenuRepository;
import com.junhee92kr.common.code.ErrorCode;
import com.junhee92kr.common.exception.BaseException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {
	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private final MenuMapper menuMapper;

	public List<MenuResponseDto> getMenuList() {
		List<MenuEntity> menuList = menuRepository.findAll();
		return menuList.stream().map(menuMapper::entityToResDto).collect(Collectors.toList());
	}

	public MenuResponseDto getMenu(Long id) {
		MenuEntity entity = menuRepository.findById(id)
				.orElseThrow(() -> new BaseException(ErrorCode.MENU_OPTION_NOT_FOUND));
		return menuMapper.entityToResDto(entity);
	}

	@Transactional
	public void saveMenu(MenuRequestDto menuDto) {
		MenuEntity saveMenuEntity = menuMapper.saveReqToEntity(menuDto);
		menuRepository.save(saveMenuEntity);
	}

	@Transactional
	public void deleteMenuById(Long id) {
		if (!menuRepository.existsById(id)) {
			throw new BaseException(ErrorCode.MENU_NOT_FOUND);
		}
		menuRepository.deleteById(id);
	}
}
