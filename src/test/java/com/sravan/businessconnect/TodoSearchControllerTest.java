package com.sravan.businessconnect;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.sravan.businessconnect.app.dao.Todo;
import com.sravan.businessconnect.controller.TodoSearchController;
import com.sravan.businessconnect.todo.service.TodoMapper;
import com.sravan.businessconnect.todo.service.TodoSearchService;

/*
To test the Controllers, we can use @WebMvcTest. It will auto-configure the Spring MVC 
infrastructure for our unit tests.

In most of the cases, @WebMvcTest will be limited to bootstrap a single controller. It is 
used along with @MockBean to provide mock implementations for required dependencies.

@WebMvcTest also auto-configures MockMvc which offers a powerful way of easy testing MVC 
controllers without starting a full HTTP server.

The get(…) method call can be replaced by other methods corresponding to HTTP verbs like put(), 
post(), etc. Please note that we are also setting the content type in the request.

MockMvc is flexible, and we can create any request using it.

*/

@RunWith(SpringRunner.class)
@WebMvcTest(TodoSearchController.class)
public class TodoSearchControllerTest {
	
	@Autowired
    private MockMvc mvc;
 
    @MockBean
    @Qualifier("todoSearchService")
    private TodoSearchService todoSearchService;
    
    @Before
    public void setUp() throws Exception {
    }
    
    @Test
    @WithMockUser(username = "admin", password = "secret")
    public void givenEmployees_whenGetEmployees_thenReturnJsonArray()
      throws Exception {
         
		Todo todo = new Todo();
		todo.setId((long)1);//(long)i
		todo.setTitle("xyz");
		todo.setDescription("desc");
		todo.setModifiedByUser("user");
		todo.setVersion(1l);
		todo.setCreationTime(new Date());
		todo.setModificationTime(new Date());
		todo.setCreatedByUser("user");
		
		List<Todo> todos = new LinkedList<>();
		todos.add(todo);
     
        given(todoSearchService.findBySearchTerm(todo.getTitle()))
        .willReturn(TodoMapper.mapEntitiesIntoDTOs(todos));
        
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken("admin","secret");
        mvc.perform(get("/todo/search?searchTerm=xyz")
          .accept(MediaType.APPLICATION_JSON)
          /*.requestAttr("searchTerm", "xyz")*/
          /*.principal(auth)*/)
         /* .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(1)))*/
          .andExpect(jsonPath("$[0].title", is(todo.getTitle())));
    }
}
