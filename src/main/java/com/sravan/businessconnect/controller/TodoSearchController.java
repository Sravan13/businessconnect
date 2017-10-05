package com.sravan.businessconnect.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sravan.businessconnect.todo.service.TodoDTO;
import com.sravan.businessconnect.todo.service.TodoSearchService;


@RestController
@CrossOrigin
final class TodoSearchController {

    @Autowired
    @Qualifier("todoSearchService")
    private TodoSearchService todoSearchService;

    /*@Autowired
    public TodoSearchController(RepositoryTodoSearchService searchService) {
        this.todoSearchService = searchService;
    }*/

   
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
}