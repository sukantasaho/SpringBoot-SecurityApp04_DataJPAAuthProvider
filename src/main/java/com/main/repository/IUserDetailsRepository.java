package com.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.entity.UserDetailsInfo;

public interface IUserDetailsRepository extends JpaRepository<UserDetailsInfo, Integer>{

	public Optional<UserDetailsInfo> findByUname(String uname);
}
