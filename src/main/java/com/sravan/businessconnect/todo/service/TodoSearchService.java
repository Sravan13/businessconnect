package com.sravan.businessconnect.todo.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

//@Service
public interface TodoSearchService {
	
	public List<TodoDTO> findBySearchTerm(String searchTerm);
	
	public List<TodoDTO> findBySearchTerm(String searchTerm,Sort sort);
	
	public List<TodoDTO> findBySearchTermWithPagination(String searchTerm, Pageable pageable);
}
