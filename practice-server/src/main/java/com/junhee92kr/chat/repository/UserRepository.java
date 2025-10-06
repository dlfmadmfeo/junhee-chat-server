package com.junhee92kr.chat.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.junhee92kr.chat.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>   {
	public Optional<User> findByEmail(String email);
	
	public Optional<User> findByPhone(String phone);
}
