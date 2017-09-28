package com.sravan.businessconnect.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

//import ch.qos.logback.core.filter.Filter;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	SimpleUrlAuthenticationSuccessHandler simpleUrlAuthenticationSuccessHandler;
	
	/*@Autowired
	// other way of configure method
	@Override
	protected void configure(HttpSecurity http) throws Exception {
				http.csrf().disable().authorizeRequests().antMatchers("/*", "/register", "/forgotPassword").permitAll()
				.antMatchers("/admin/**").hasRole("ADMIN").and().exceptionHandling()
				.authenticationEntryPoint(restAuthenticationEntryPoint).and()
				.formLogin().successHandler(authenticationSuccessHandler)
				.failureHandler(new SimpleUrlAuthenticationFailureHandler()).and().logout().permitAll()
				;
	}*/
	
	
	 // this for setting in memory credentials
	/*@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("john123").password("password").roles("USER");
	}*/
	
	/*
	 * for basic authentication
	 * @Autowired
	private AuthenticationEntryPoint authEntryPoint;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.anyRequest().authenticated()
				.and().httpBasic()
				.authenticationEntryPoint(authEntryPoint)
				.and().formLogin().successHandler(simpleUrlAuthenticationSuccessHandler)
				.failureHandler(new SimpleUrlAuthenticationFailureHandler());
	}*/
	
	@Autowired
	DigestAuthenticationEntryPoint digestAuthenticationEntryPoint;

	/*
	 // for DigestAuthentication
	 * @Override 
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.anyRequest().authenticated()
				.and().addFilter(digestFilter(digestAuthenticationEntryPoint()))
				.formLogin().successHandler(simpleUrlAuthenticationSuccessHandler)
				.failureHandler(new SimpleUrlAuthenticationFailureHandler());
	}*/
	
	@Override 
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.anyRequest().authenticated().and()
				.httpBasic().authenticationEntryPoint(digestAuthenticationEntryPoint())
				/*.and().addFilterAfter(digestFilter(digestAuthenticationEntryPoint()),BasicAuthenticationFilter.class)*/
				.and().addFilter(digestFilter(digestAuthenticationEntryPoint()))
				.formLogin().successHandler(simpleUrlAuthenticationSuccessHandler)
				.failureHandler(new SimpleUrlAuthenticationFailureHandler());
		
		http.headers().cacheControl().disable();
	}
	
	@Bean
	public DigestAuthenticationFilter digestFilter(DigestAuthenticationEntryPoint digestAuthenticationEntryPoint) throws Exception{
		
		DigestAuthenticationFilter filter = new DigestAuthenticationFilter();
		filter.setAuthenticationEntryPoint(digestAuthenticationEntryPoint);
		filter.setUserCache(digestUserCache());
		filter.setUserDetailsService(userDetailsServiceBean());
		return filter;
	}
	
	@Bean
	UserCache digestUserCache() throws Exception {
		return new SpringCacheBasedUserCache(new ConcurrentMapCache("digestUserCache"));
	}
	
	@Bean
	public DigestAuthenticationEntryPoint digestAuthenticationEntryPoint(){
		
		DigestAuthenticationEntryPoint entryPoint = new DigestAuthenticationEntryPoint();
		entryPoint.setRealmName("sravan");
		entryPoint.setKey("xyz");
		entryPoint.setNonceValiditySeconds(60);
		
		return entryPoint;
	}
	
	@Override
	@Bean
	public UserDetailsService userDetailsServiceBean() throws Exception {
	    return super.userDetailsServiceBean();
	}

}