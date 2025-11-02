package com.junhee92kr.cafe.service;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.junhee92kr.cafe.dto.MenuOptionRequestDto;
import com.junhee92kr.cafe.dto.MenuOptionResponseDto;
import com.junhee92kr.cafe.entity.MenuOptionEntity;
import com.junhee92kr.cafe.mapper.MenuOptionMapper;
import com.junhee92kr.cafe.repository.MenuOptionRepository;
import com.junhee92kr.common.code.ErrorCode;
import com.junhee92kr.common.exception.BaseException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuOptionService {
	@Autowired
	private MenuOptionRepository menuOptionRepository;

	@Autowired
	private MenuOptionMapper menuOptionMapper;

	public List<MenuOptionResponseDto> getMenuOptionList() {
		List<MenuOptionEntity> entityList = menuOptionRepository.findAll();
		return entityList.stream().map(menuOptionMapper::entityToResDto).collect(Collectors.toList());
	}

	public MenuOptionResponseDto getMenuOption(Long id) {
		MenuOptionEntity entity = menuOptionRepository.findById(id)
				.orElseThrow(() -> new BaseException(ErrorCode.MENU_OPTION_NOT_FOUND));
		return menuOptionMapper.entityToResDto(entity);
	}

	@Transactional
	public void saveMenuOption(MenuOptionRequestDto dto) {
		try {
			MenuOptionEntity entity = menuOptionMapper.saveReqToEntity(dto);
			menuOptionRepository.save(entity);
		} catch (DataIntegrityViolationException e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				throw new BaseException(ErrorCode.MENU_OPTION_DUPLICATED);
			}
			throw e;
		}
	}

	@Transactional
	public void deleteMenuOptionById(Long id) {
		if (!menuOptionRepository.existsById(id)) {
			throw new BaseException(ErrorCode.MENU_OPTION_NOT_FOUND);
		}
		menuOptionRepository.deleteById(id);
	}
}
