package com.sravan.businessconnect.todo.service;
import org.springframework.data.jpa.domain.Specification;

import com.sravan.businessconnect.app.dao.Todo;


public final class TodoSpecifications {
	
	 private TodoSpecifications() {}
	 
	 static Specification<Todo> titleOrDescriptionContainsIgnoreCase(String searchTerm) {
		 
		 return (root,query,cb) -> {
			 String containsLikePattern = getContainsLikePattern(searchTerm);
			 
			 return cb.or(
	                    cb.like(cb.lower(root.<String>get("title")), containsLikePattern),
	                    cb.like(cb.lower(root.<String>get("description")), containsLikePattern)
	            );
		 };
		 
	 }
	 
	 private static String getContainsLikePattern(String searchTerm){
		 if(searchTerm == null || searchTerm.isEmpty()){
			 return "%";
		 }else{
			 return "%"+searchTerm+"%";
		 }
	 }

}
