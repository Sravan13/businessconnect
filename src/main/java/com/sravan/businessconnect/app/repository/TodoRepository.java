package com.sravan.businessconnect.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.web.context.SaveContextOnUpdateOrErrorResponseWrapper;

import com.sravan.businessconnect.app.dao.Todo;
import java.util.stream.Stream;

@org.springframework.stereotype.Repository
public interface TodoRepository extends Repository<Todo, Long> {
	
	Todo findById(long id);
	
	@Query("SELECT t.title from Todo t where t.id = :id")
	String findTitleById(@Param("id") Long id);
	
	List<Todo> findByTitle(String title);
	
	Todo save(Todo todo);
	
	//Optional<Todo> findById(Long id);  // Asynch method
	
	//@Query("SELECT t.title from Todo t where t.id = :id")	
	//Optional<String>  findTitleById(long id); // Asynch method
	
	//Stream<Todo> findByTitle(String title); // Asynch method

}

	