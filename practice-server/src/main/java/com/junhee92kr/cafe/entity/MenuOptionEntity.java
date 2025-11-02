package com.junhee92kr.cafe.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.junhee92kr.cafe.enums.MenuOptionType;
import com.junhee92kr.cafe.enums.MenuType;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name="menu_opt", catalog="cafe")
@Entity
public class MenuOptionEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Nullable
	private Long menuId;
	
	@Nullable
	@Enumerated(EnumType.STRING)
	private MenuType menuType;
	
	private String name;
	
	private String label;
	
	@Enumerated(EnumType.STRING)
	private MenuOptionType type;	
	
	private boolean isRequired;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdDate;
	
	@UpdateTimestamp
	private LocalDateTime updatedDate;
}
