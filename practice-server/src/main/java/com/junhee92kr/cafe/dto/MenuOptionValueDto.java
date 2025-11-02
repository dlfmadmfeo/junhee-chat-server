package com.junhee92kr.cafe.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuOptionValueDto {
	private Long id;
	private Long menuOptId; // 참조
    private String value;         // 예: "ICE"
    private Integer extraPrice;   // 예: 500
}
