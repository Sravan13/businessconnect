package com.sravan.businessconnect.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.base.Preconditions;
import com.sravan.businessconnect.app.dao.Category;
import com.sravan.businessconnect.bean.Foo;
import com.sravan.businessconnect.hateos.event.ResourceCreatedEvent;
import com.sravan.businessconnect.hateos.event.SingleResourceRetrievedEvent;
import com.sravan.businessconnect.properties.ServerProperties;
import com.sravan.businessconnect.sec.dao.User;
import com.sravan.businessconnect.service.HomeService;
import com.sravan.businessconnect.util.RestPreconditions;

@Controller
@RequestMapping("/abc")
@CrossOrigin
public class HomeController {
	
	private static final Logger logger =  Logger.getLogger(HomeController.class);
	
	@Autowired
	private HomeService homeService;
	
	@Autowired
	@Qualifier("clusterDetails")
    private ServerProperties serverProperties;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	// curl -i --user admin:secret -H Accept:application/json http://localhost:8080/abc/categories
	@RequestMapping(value="/categories",method=RequestMethod.GET)
	public @ResponseBody List<Category> getCategories(){
		
		List<Category>  categories = homeService.getCategories();
		
		logger.info("categories size is : "+categories.size());
		return categories;
	}
	
	// curl -i --user admin:secret -H Accept:application/json http://localhost:8080/abc/categories
	@RequestMapping(value="/users",method=RequestMethod.GET)
	public @ResponseBody List<User> getUsers(){
		
		List<User>  users = homeService.getUsers();
		
		logger.info("user size is : "+users.size());
		return users;
	}
	
	/*// curl -i --user admin:secret -H Accept:application/xml http://localhost:8080/abc/foos/1
	@RequestMapping(value="/foos/{id}",method=RequestMethod.GET)
	public @ResponseBody Foo findById(@PathVariable long id) {
		
		Foo foo = new Foo();
		foo.setId(1);
		foo.setName("sravan");
		return foo;
	}*/
	
	
	// curl -i --user admin:secret -X PUT -H "Content-Type: application/json" -H "Accept: application/xml" -d ‘{“id”:”83”,”name”:”klik”}’ http://localhost:8080/abc/foos/1
	@RequestMapping(method=RequestMethod.PUT, value="/foos/{id}")
	public @ResponseBody Foo updateFoo(@RequestBody Foo foo, @PathVariable String id) {
		logger.info(" Foo got updated "+foo.getId()+" : "+foo.getName());
		return foo;
	}
	
	@RequestMapping(value="/foos", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody Foo createFoo(@RequestBody Foo foo,final HttpServletResponse response) {
		    Preconditions.checkNotNull(foo);
			logger.info(" Foo got created "+foo.getId()+" : "+foo.getName());
			
			final long idOfCreatedResource = foo.getId();
			eventPublisher.publishEvent(new ResourceCreatedEvent(this, response, idOfCreatedResource));
			return foo;
	}
	
	// curl -i --user admin:secret -H Accept:application/json http://localhost:8080/abc/foos/1
	@RequestMapping(value = "/foos/{id}", method = RequestMethod.GET)
    public @ResponseBody Foo findById(@PathVariable("id") final Long id, final HttpServletResponse response) {
        final Foo resourceById = RestPreconditions.checkFound(new Foo(id,"xyz")); // service.findOne(id)

        eventPublisher.publishEvent(new SingleResourceRetrievedEvent(this, response));
        return resourceById;
    }
	
/*	@RequestMapping(params = { "page", "size" }, method = RequestMethod.GET)
    public @ResponseBody List<Foo> findPaginated(@RequestParam("page") final int page, @RequestParam("size") final int size, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        final Page<Foo> resultPage = service.findPaginated(page, size);
        if (page > resultPage.getTotalPages()) {
            throw new MyResourceNotFoundException();
        }
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<Foo>(Foo.class, uriBuilder, response, page, resultPage.getTotalPages(), size));

        return resultPage.getContent();
    }*/
	
}
