package com.junhee92kr.cafe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.junhee92kr.cafe.dto.MenuOptionRequestDto;
import com.junhee92kr.cafe.dto.MenuOptionResponseDto;
import com.junhee92kr.cafe.service.MenuOptionService;
import com.junhee92kr.common.response.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/menu-option")
@CrossOrigin(origins = { "http://localhost:5173", "http://localhost:3000", "http://junhee92kr.com" })
public class MenuOptionController {
	@Autowired
	private MenuOptionService menuOptionService;

	@GetMapping(path = "/list")
	public ApiResponse<List<MenuOptionResponseDto>> getMenuOptionList() {
		List<MenuOptionResponseDto> dtoList = menuOptionService.getMenuOptionList();
		return ApiResponse.success(dtoList);
	}

	@GetMapping(path = "/{id}")
	public ApiResponse<MenuOptionResponseDto> getMenuOption(Long id) {
		MenuOptionResponseDto dto = menuOptionService.getMenuOption(id);
		return ApiResponse.success(dto);
	}

	@PostMapping
	public ApiResponse<Void> saveMenuOption(MenuOptionRequestDto dto) {
		menuOptionService.saveMenuOption(dto);
		return ApiResponse.success(null);
	}

	@DeleteMapping(path = "/{id}")
	public ApiResponse<Void> deleteMenu(@PathVariable Long id) {
		menuOptionService.deleteMenuOptionById(id);
		return ApiResponse.success(null);
	}
}
