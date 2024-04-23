package com.main.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.main.entity.UserDetailsInfo;
import com.main.repository.IUserDetailsRepository;

@Service
public class UserDetailsServiceImp implements  IUserDetailsService{

	@Autowired
	private IUserDetailsRepository repo;
	
	@Override
	public String registerUser(UserDetailsInfo details) {
		//Encrypt the password
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String pwd = encoder.encode(details.getPwd());
		details.setPwd(pwd);
	    int unid = repo.save(details).getUnid();
		return "User registration completed with id-"+unid;
	}
	@Override
	public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		   Optional<UserDetailsInfo> opt = repo.findByUname(username);
		   if(opt.isEmpty())
		   {
			   throw new IllegalArgumentException("User Not Found");
		   }
		   else
		   {
			  //convert Set<String> roles to Set<SGA> roles
			   UserDetailsInfo details = opt.get();
				/*Set<GrantedAuthority> gaRoles = new HashSet<>();
				for(String role : details.getRoles())
				{
				   SimpleGrantedAuthority sga = new SimpleGrantedAuthority(role);
				   gaRoles.add(sga);
				}
			   //Convert UserDetails Object (Entity Object) to User Object
			   User user = new User(details.getUname(), details.getPwd(), gaRoles);*/
			   User user = new User(details.getUname(), details.getPwd(), details.getRoles().stream().map(role->
			   new SimpleGrantedAuthority(role)).collect(Collectors.toSet()));
			   return user;
		   }
		 
	}

}
