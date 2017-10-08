package com.sravan.businessconnect.todo.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.sravan.businessconnect.app.dao.Todo;

//@Service
public interface TodoSearchService {
	
	public List<TodoDTO> findBySearchTerm(String searchTerm);
	
	public List<TodoDTO> findBySearchTerm(String searchTerm,Sort sort);
	
	public List<TodoDTO> findBySearchTermWithPagination(String searchTerm, Pageable pageable);
	
	public TodoDTO save();
	
	public List<TodoSearchResultDTO> findBySearchTermWithCustomRepo(String searchTerm);
}
