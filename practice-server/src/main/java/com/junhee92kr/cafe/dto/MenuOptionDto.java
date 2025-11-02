package com.junhee92kr.cafe.dto;

import java.util.List;

import com.junhee92kr.cafe.enums.MenuOptionType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuOptionDto {
    private String name;           // 내부 식별용 (예: "temperature")
    private String label;          // 사용자 표시용 (예: "온도")
    private MenuOptionType type;   // SINGLE, MULTI
    private boolean required;      // 필수 여부

    private List<MenuOptionValueDto> values;
}

