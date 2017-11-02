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

/*
 * 
 
We can now implement our custom repository interface by following these steps:

Create a custom repository class that implements the CustomTodoRepository interface. 
By default, the name of a custom repository class must follow this syntax: 
[The name of the repository interface]Impl. Because the name of our repository interface is 
TodoRepository, the name of our custom repository class must be TodoRepositoryImpl.

Annotate the created class with the @Repository annotation.
Create an SQL query that returns the id and title of todo entries, whose title or description contains
the given search term, and sorts the query results in ascending order by using the value of the title 
column. Set this SQL query as the value of a static final field.
 
Add a final NamedParameterJdbcTemplate field to repository class and inject the value of this 
field by using constructor injection.

Implement the findBySearchTerm() method by following these steps:
Annotate the method with the @Transactional annotation and mark the transaction as read-only. 
This ensures that our SQL query is always invoked inside a read-only transaction.

Create a Map object, which contains the query parameters of our SQL query, and put the search term 
given as a method parameter into the created map.
Invoke the SQL query and transform the query results into a list of TodoSearchResultDTO objects by 
using the BeanPropertyRowMapper class. We can use this method as long as the aliases of the selected columns
matches with the property names of the “target class”.
Return the query results.

*/