package com.sravan.businessconnect.app.dao;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
 
@Entity
@Table(name = "todos")
@NamedNativeQueries({
	@NamedNativeQuery(name="Todo.nativeFindByTitleIs",query="SELECT * FROM todos t WHERE t.title = 'abc'",resultClass=Todo.class),
	@NamedNativeQuery(name = "Todo.findBySearchTermNamedNative",
	query="SELECT * FROM todos t WHERE " +
	"LOWER(t.title) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
	"LOWER(t.description) LIKE LOWER(CONCAT('%',:searchTerm, '%'))",
	resultClass = Todo.class
	)	
})

@NamedQueries({
	@NamedQuery(name = "Todo.findByTitleIs",query = "SELECT t FROM Todo t WHERE t.title = 'abc'"),
	@NamedQuery(name = "Todo.findBySearchTermNamed",
	query = "SELECT t FROM Todo t WHERE " +
	"LOWER(t.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
	"LOWER(t.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))"
	)
})


public class Todo {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
    @Column(name = "creation_time", nullable = false)
	@Temporal(TemporalType.DATE)
    private Date creationTime;
 
    @Column(name = "description", length = 500)
    private String description;
 
    @Column(name = "modification_time")
	@Temporal(TemporalType.DATE)
    private Date modificationTime;
 
    @Column(name = "title", nullable = false, length = 100)
    private String title;
 
    @Version
    private long version;
    
	public Todo() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getModificationTime() {
		return modificationTime;
	}

	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}
    
}