package com.junhee92kr.cafe.dto;

import java.time.LocalDateTime;

import com.junhee92kr.cafe.enums.MenuOptionType;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenuOptionResponseDto {
	private Long id;	
	
	private Long menuId;
	
	private String name;
	
	private String label;
	
	private MenuOptionType type;	
	
	private boolean isRequired;
	
	private LocalDateTime createdDate;
	
	private LocalDateTime updatedDate;
}
