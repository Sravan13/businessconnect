package com.sravan.businessconnect.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sravan.businessconnect.app.dao.Todo;

@org.springframework.stereotype.Repository
public interface TodoRepository extends  JpaRepository<Todo, Long> , JpaSpecificationExecutor<Todo> , CustomTodoRepository {
	
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
	
	
	
	@Query("SELECT t FROM Todo t WHERE " +
            "LOWER(t.title) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
    List<Todo> findBySearchTermListPageable(@Param("searchTerm") String searchTerm, 
                                Pageable pageRequest);
 
    @Query("SELECT t FROM Todo t WHERE " +
            "LOWER(t.title) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
    Page<Todo> findBySearchTermPagePageable(@Param("searchTerm") String searchTerm, 
                                Pageable pageRequest);
                                 
    @Query("SELECT t FROM Todo t WHERE " +
            "LOWER(t.title) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
    Slice<Todo> findBySearchTermSlicePageable(@Param("searchTerm") String searchTerm, 
                                 Pageable pageRequest);
	
	
	
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

	