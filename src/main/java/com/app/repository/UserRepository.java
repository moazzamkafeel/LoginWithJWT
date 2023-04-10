package com.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.model.UserInfo;

public interface UserRepository extends JpaRepository<UserInfo, Integer>{

	Optional<UserInfo> findByUsername(String username);
}
