package com.junhee92kr.cafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.junhee92kr.cafe.entity.MenuOptionEntity;

@Repository
public interface MenuOptionRepository extends JpaRepository<MenuOptionEntity, Long> {

}
