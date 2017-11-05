package com.sravan.businessconnect;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * 
As the name suggests, integration tests focus on integrating different layers of the 
application. That also means no mocking is involved.

Ideally, we should keep the integration tests separated from the unit tests and should not 
run along with the unit tests. We can do that by using a different profile to only run the 
integration tests. A couple of reasons for doing this could be that the integration tests are
 time-consuming and might need an actual database to execute.

However, in this article, we won’t focus on that and we’ll instead make use of the in-memory 
H2 persistence storage.

The integration tests need to start up a container to execute the test cases. Hence, some 
additional setup is required for this – all of this is easy in Spring Boot:

The @SpringBootTest annotation can be used when we need to bootstrap the entire container. 
The annotation works by creating the ApplicationContext that will be utilized in our tests.

We can use the webEnvironment attribute of @SpringBootTest to configure our runtime environment.
We are using WebEnvironment.RANDOM_PORT so that the container will start at any random port. It will be
 helpful if several integration tests are running in parallel on the same machine.

We can use the @TestPropertySource annotation to configure locations of properties files specific to our 
tests. Please note that the property file loaded with @TestPropertySource will override the 
existing application.properties file.

 @TestPropertySource. It is a class-level annotation that is used to specify which 
 properties files should be loaded when running the test class.

Test property sources have the highest precedence than all other properties sources.
 That means Test source will override all other properties.

**/
@RunWith(SpringRunner.class)//App.class
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT,classes= App.class)
@AutoConfigureMockMvc
@TestPropertySource(
		  locations = "classpath:application-integrationtest.properties")
public class TodoControllerIntergrationTest {
	
	@Autowired
    private MockMvc mvc;
	
    @Test
    @WithMockUser(username = "admin", password = "secret")
    public void givenEmployees_whenGetEmployees_thenReturnJsonArray()
      throws Exception {
         
        mvc.perform(get("/todo/search?searchTerm=abc")
          .accept(MediaType.APPLICATION_JSON)
          /*.requestAttr("searchTerm", "xyz")*/
          /*.principal(auth)*/)
         /* .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(1)))*/
          .andExpect(jsonPath("$[0].title", is("abc")));
    }

}
