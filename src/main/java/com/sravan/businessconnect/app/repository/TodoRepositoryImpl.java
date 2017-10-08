package com.sravan.businessconnect.app.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sravan.businessconnect.todo.service.TodoSearchResultDTO;

/*
 * Spring Data JPA provides an excellent support for implementing CRUD operations and 
 * creating database queries, sometimes we need to do things that are not supported by it. 
 * For example, Spring Data JPA doesn’t provide a built-in support for querying DTOs by using
 *  SQL.
 *  
 *  example is "sql joins" . To handle this kind of situation we create custom repository.  
 */

@Repository
public class TodoRepositoryImpl implements CustomTodoRepository {
	
	private static final String SEARCH_TODO_ENTRIES = "SELECT id, title FROM todos t WHERE " +
            "LOWER(t.title) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%',:searchTerm, '%')) " +
            "ORDER BY t.title ASC";
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<TodoSearchResultDTO> findBySearchTerm(String searchTerm) {
		Map<String, String> queryParams = new HashMap<>();
        queryParams.put("searchTerm", searchTerm);
        
        List<TodoSearchResultDTO> searchResults = jdbcTemplate.query(SEARCH_TODO_ENTRIES,queryParams,
        		new BeanPropertyRowMapper<>(TodoSearchResultDTO.class));
        
		return searchResults;
	}

}
