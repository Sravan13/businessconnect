package com.sravan.businessconnect.todo.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

//@Service
public interface TodoSearchService {
	
	public List<TodoDTO> findBySearchTerm(String searchTerm);

}
