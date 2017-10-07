package com.sravan.businessconnect.todo.service;

import static com.sravan.businessconnect.todo.service.TodoSpecifications.titleOrDescriptionContainsIgnoreCase;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sravan.businessconnect.app.dao.Todo;
import com.sravan.businessconnect.app.repository.TodoRepository;

@Service("todoSearchService")
public class RepositoryTodoSearchService implements TodoSearchService {
	
	
	
	@Autowired
	TodoRepository todoRepository;

	//@Transactional(readOnly = true)
	@Override
	public List<TodoDTO> findBySearchTerm(String searchTerm) {
		List<Todo> searchResults = todoRepository.findAll(titleOrDescriptionContainsIgnoreCase(searchTerm), orderBy());
		return TodoMapper.mapEntitiesIntoDTOs(searchResults);
	}
	
	@Override
	public List<TodoDTO> findBySearchTerm(String searchTerm, Sort sort) {
		List<Todo> searchResults = todoRepository.findAll(titleOrDescriptionContainsIgnoreCase(searchTerm), sort);
		return TodoMapper.mapEntitiesIntoDTOs(searchResults);
	}
	
	@Override
	public TodoDTO save() {
		Todo todo = new Todo();
		todo.setVersion(11);
		todo.setTitle("abc");
		todo.setDescription("todo description 11");
		try{
		todo = todoRepository.save(todo);
		}catch (DataIntegrityViolationException e) {
			e.printStackTrace();
		}
		
		return TodoMapper.mapEntitiesIntoDTOs(Arrays.asList(todo)).get(0);
	}
	
	
	public List<TodoDTO> findBySearchTermWithPagination(String searchTerm, Pageable pageable){
		Page<Todo> pageSearchResults = todoRepository.findAll(titleOrDescriptionContainsIgnoreCase(searchTerm), pageable);
		return TodoMapper.mapEntitiesIntoDTOs(pageSearchResults.getContent());
	}
	
	
	private Sort orderBy() {
	    return new Sort(Sort.Direction.DESC, "title", "description");
	}
	
	private Sort diffSortorderBy() {
	    return new Sort(Sort.Direction.DESC, "description")
	                .and(new Sort(Sort.Direction.ASC, "title"));
	}







}
