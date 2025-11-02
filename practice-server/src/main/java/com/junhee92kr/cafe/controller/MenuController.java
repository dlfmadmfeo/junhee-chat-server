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

import com.junhee92kr.cafe.dto.MenuRequestDto;
import com.junhee92kr.cafe.dto.MenuResponseDto;
import com.junhee92kr.cafe.service.MenuService;
import com.junhee92kr.common.response.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/menu")
@CrossOrigin(origins = { "http://localhost:5173", "http://localhost:3000", "http://junhee92kr.com" })
public class MenuController {
	@Autowired
	private MenuService menuService;

	@GetMapping(path = "/list")
	public ApiResponse<List<MenuResponseDto>> getMenuList() {
		List<MenuResponseDto> menuResponseDtoList = menuService.getMenuList();
		return ApiResponse.success(menuResponseDtoList);
	}

	@GetMapping(path = "/{id}")
	public ApiResponse<MenuResponseDto> getMenu(Long id) {
		MenuResponseDto dto = menuService.getMenu(id);
		return ApiResponse.success(dto);
	}

	@PostMapping
	public ApiResponse<Void> saveMenu(MenuRequestDto dto) {
		menuService.saveMenu(dto);
		return ApiResponse.success(null);
	}

	@DeleteMapping(path = "/{id}")
	public ApiResponse<Void> deleteMenu(@PathVariable Long id) {
		menuService.deleteMenuById(id);
		return ApiResponse.success(null);
	}
}
