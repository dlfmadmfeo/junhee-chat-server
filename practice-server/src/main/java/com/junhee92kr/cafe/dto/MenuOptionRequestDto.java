package com.junhee92kr.cafe.dto;

import com.junhee92kr.cafe.enums.MenuOptionType;
import com.junhee92kr.cafe.enums.MenuType;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenuOptionRequestDto {
	private Long id;	
	
	private Long menuId;
	
	private MenuType menuType;
	
	private String name;
	
	private String label;
	
	private MenuOptionType type;	
	
	private boolean isRequired;
	
	private Integer displayOrder;
}
