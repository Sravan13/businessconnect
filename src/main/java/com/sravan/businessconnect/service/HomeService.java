package com.sravan.businessconnect.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sravan.businessconnect.app.dao.Category;
import com.sravan.businessconnect.app.repository.CategoryRepo;
import com.sravan.businessconnect.sec.dao.User;
import com.sravan.businessconnect.sec.repository.UserRepository;

@Service
public class HomeService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	public List<Category> getCategories(){
		
		try{
			List<Category> categories =  categoryRepo.findAll();
			return  categories;
		}catch(Exception ex){
			return Collections.emptyList();
		}
	}
	
	public List<User> getUsers(){
		
		try{
			List<User> categories =  userRepo.findAll();
			return  categories;
		}catch(Exception ex){
			return Collections.emptyList();
		}
	}

}
