package com.sravan.businessconnect.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.web.context.SaveContextOnUpdateOrErrorResponseWrapper;

import com.sravan.businessconnect.app.dao.Todo;
import java.util.stream.Stream;

@org.springframework.stereotype.Repository
public interface TodoRepository extends Repository<Todo, Long> {
	
	Todo findById(long id);
	
	@Query("SELECT t.title from Todo t where t.id = :id")
	String findTitleById(@Param("id") Long id);
	
	List<Todo> findByTitle(String title);
	
	Todo save(Todo todo);
	
	@Query(value = "SELECT * FROM todos t WHERE " +
            "LOWER(t.title) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%',:searchTerm, '%'))",
            nativeQuery = true
    )
    List<Todo> findBySearchTermNative(@Param("searchTerm") String searchTerm);
	
	@Query(nativeQuery = true)
	public List<Todo> nativeFindByTitleIs();
	
	public List<Todo> findByTitleIs();
	
//	List<Todo> findBySearchTermNamed(@Param("searchTerm") String searchTerm);
	
	@Query
	List<Todo> findBySearchTermNamed(@Param("searchTerm") String searchTerm);
	
	@Query(nativeQuery = true)
	List<Todo> findBySearchTermNamedNative(@Param("searchTerm") String searchTerm);
	
	
	
	//Optional<Todo> findById(Long id);  // Asynch method
	
	//@Query("SELECT t.title from Todo t where t.id = :id")	
	//Optional<String>  findTitleById(long id); // Asynch method
	
	//Stream<Todo> findByTitle(String title); // Asynch method
	
	
	// follow as below if we want to execute query async
	
	/*@Async
    @Query("SELECT t.title FROM Todo t where t.id = :id") 
    Future<String> findTitleById(@Param("id") Long id);
     
    @Async
    @Query("SELECT t.title FROM Todo t where t.id = :id") 
    Future<Optional<String>> findTitleById(@Param("id") Long id);
 
    @Async
    Future<Todo> findById(Long id);
     
    @Async
    Future<Optional<Todo>> findById(Long id);
 
    @Async
    Future<List<Todo>> findByTitle(String title);
     
    @Async
    Future<Stream<Todo>> findByTitle(String title);*/

}

	