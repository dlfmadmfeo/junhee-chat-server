package com.junhee92kr.cafe.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.junhee92kr.cafe.enums.MenuType;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenuResponseDto {
	private Long id;
	
	private String menuCode;
	
	private String name;
	
	private MenuType type;
	
	private Integer price;
	
	private String imageUrl;
	
	private String description;	
	
	@JsonProperty("isAvailable")
	private boolean isAvailable;
	
	@JsonProperty("isDeleted")
	private boolean isDeleted;
	
	@JsonProperty("isEvent")
	private boolean isEvent;
	
	private LocalDateTime eventStartDate;
	
	private LocalDateTime eventEndDate;	
}
