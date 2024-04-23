package com.main.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.main.service.IUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private IUserDetailsService userService;
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		  //configure jdbc as authntication provider
		 auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
	}
	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		//place the authentication and authorization logics for the request url
		http.authorizeRequests().antMatchers("/","/user/registerForm","/user/register","/user/login").permitAll()//no Authentication and Authorization 
		.antMatchers("/bank/offers").authenticated()//only Authentication
		.antMatchers("/bank/showBalance").hasAnyAuthority("CUSTOMER","MANAGER")
		.antMatchers("/bank/approveLoan").hasAuthority("MANAGER")
		.anyRequest().authenticated()
		//.and().httpBasic()
		.and().formLogin()
		.defaultSuccessUrl("/", true)
		.loginPage("/user/login").loginProcessingUrl("/login").failureUrl("/user/login?error")
		//.and().rememberMe()
	    .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	    //.logoutUrl("/logout")
	    //.logoutSuccessUrl("/user/login?logout")
		.and().exceptionHandling().accessDeniedPage("/bank/denied")
		.and().sessionManagement().maximumSessions(2).maxSessionsPreventsLogin(true);
		 
	}
	
}
