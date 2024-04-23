package com.main.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.main.entity.UserDetailsInfo;

public interface IUserDetailsService extends UserDetailsService{

	public String registerUser(UserDetailsInfo details);
}
