package com.junhee92kr.cafe.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.junhee92kr.cafe.enums.MenuOptionType;

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
@Table(name="menu_cart_opt", catalog="cafe")
@Entity
public class MenuCartOptionEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;	
	
	private Long cartItemId;
	
	private Long optGrpId;
	
	private Long optValId;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdDate;
}
