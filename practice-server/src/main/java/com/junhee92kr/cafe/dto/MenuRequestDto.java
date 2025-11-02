package com.junhee92kr.cafe.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.junhee92kr.cafe.enums.MenuType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenuRequestDto {
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
	
	@Schema(example = "2025-10-11 00:43:36")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime eventStartDate;
	
	@Schema(example = "2025-10-11 00:43:36")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime eventEndDate;	
	
	private List<MenuOptionDto> optionList;
}
