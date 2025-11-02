package com.junhee92kr.cafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.junhee92kr.cafe.entity.MenuCartEntity;

@Repository
public interface MenuCartRepository extends JpaRepository<MenuCartEntity, Long> {

}
