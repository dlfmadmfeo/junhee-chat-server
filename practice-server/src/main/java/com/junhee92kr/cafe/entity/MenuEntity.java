package com.junhee92kr.cafe.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.junhee92kr.cafe.enums.MenuType;

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
@Table(name="menu", catalog="cafe")
@Entity
public class MenuEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;	
	
	private String menuCode;
	
	private String name;	
	
	@Enumerated(EnumType.STRING)
	private MenuType type;	
	
	private Integer price;
	
	private String imageUrl;
	
	private String description;
	
	private boolean isAvailable;
	
	private boolean isDeleted;
	
	private boolean isEvent;
	
	private LocalDateTime eventStartDate;
	
	private LocalDateTime eventEndDate;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdDate;
	
	@UpdateTimestamp
	private LocalDateTime updatedDate;
}
