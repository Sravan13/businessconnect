package com.sravan.businessconnect.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sravan.businessconnect.app.dao.Todo;
import com.sravan.businessconnect.todo.service.TodoDTO;
import com.sravan.businessconnect.todo.service.TodoSearchResultDTO;
import com.sravan.businessconnect.todo.service.TodoSearchService;


@RestController
@CrossOrigin
final class TodoSearchController {

    @Autowired
    @Qualifier("todoSearchService")
    private TodoSearchService todoSearchService;

    // curl -i --user admin:secret -H Accept:application/json http://localhost:8080/todo/save
    @RequestMapping(value = "/todo/save", method = RequestMethod.GET)
    public TodoDTO saveTodo() {
    	TodoDTO  todoDTO = todoSearchService.save();
    	return todoDTO;
    }

   
    /**
     * Finds todo entries whose title or description contains the given search term. This
     * search is case insensitive.
     * @param searchTerm    The used search term.
     * @return
     */
    
    // curl -i --user admin:secret -H Accept:application/json http://localhost:8080/todo/search?searchTerm=abc
    @RequestMapping(value = "/todo/search", method = RequestMethod.GET)
    public  List<TodoDTO> findBySearchTerm(@RequestParam("searchTerm") String searchTerm) {
        List<TodoDTO> searchResults = todoSearchService.findBySearchTerm(searchTerm);
        return searchResults;
    }
    
    // curl -i --user admin:secret -H Accept:application/json http://localhost:8080/todo/searchWithSort?searchTerm=abc&sort=title&sort=description,asc
    @RequestMapping(value = "/todo/searchWithSort", method = RequestMethod.GET)
    public  List<TodoDTO> findBySearchTerm(@RequestParam("searchTerm") String searchTerm,Sort sort) {
        List<TodoDTO> searchResults = todoSearchService.findBySearchTerm(searchTerm,sort);
        return searchResults;
    }
    
    // curl -i --user admin:secret -H Accept:application/json http://localhost:8080/todo/searchWithPagination?searchTerm=abc&page=0&size=3
    @RequestMapping(value = "/todo/searchWithPagination", method = RequestMethod.GET)
    public  List<TodoDTO> findBySearchTermWithPagination(@RequestParam("searchTerm") String searchTerm,Pageable pageable) {
        List<TodoDTO> searchResults = todoSearchService.findBySearchTermWithPagination(searchTerm,pageable);
        return searchResults;
    }
    
    // curl -i --user admin:secret -H Accept:application/json http://localhost:8080/todo/searchWithCustomRepo?searchTerm=abc
    @RequestMapping(value = "/todo/searchWithCustomRepo", method = RequestMethod.GET)
    public  List<TodoSearchResultDTO> findBySearchTermWithCustomRepo(@RequestParam("searchTerm") String searchTerm) {
        List<TodoSearchResultDTO> searchResults = todoSearchService.findBySearchTermWithCustomRepo(searchTerm);
        return searchResults;
    }
}