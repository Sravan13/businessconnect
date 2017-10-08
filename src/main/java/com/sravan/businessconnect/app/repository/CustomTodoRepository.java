package com.sravan.businessconnect.app.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.sravan.businessconnect.todo.service.TodoSearchResultDTO;

public interface CustomTodoRepository {
	List<TodoSearchResultDTO> findBySearchTerm(String searchTerm);
}
