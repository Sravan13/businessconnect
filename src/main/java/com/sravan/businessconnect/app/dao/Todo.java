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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import static com.sravan.businessconnect.todo.service.PreCondition.isTrue;
import static com.sravan.businessconnect.todo.service.PreCondition.notEmpty;
import static com.sravan.businessconnect.todo.service.PreCondition.notNull;

 
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
	"LOWER(t.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) ORDER BY t.title ASC"
	)
})


public class Todo extends BaseEntity {
	
	
	public static final int MAX_LENGTH_DESCRIPTION = 500;
    public static final int MAX_LENGTH_TITLE = 100;
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
    @Column(name = "description", length = 500)
    private String description;
    
    /*@Column(name = "creation_time", nullable = false)
	@Temporal(TemporalType.DATE)
    private Date creationTime;
 
    @Column(name = "modification_time")
	@Temporal(TemporalType.DATE)
    private Date modificationTime;*/
 
    @Column(name = "title", nullable = false, length = 100)
    private String title;
 
    @Version
    private long version;
    
    /*@Column(name = "created_by_user", nullable = false)
    @CreatedBy
    private String createdByUser;
    
    @Column(name = "modified_by_user", nullable = false)
    @LastModifiedBy
    private String modifiedByUser;*/
    
	public Todo() {
	}
	
    private Todo(Builder builder) {
        this.title = builder.title;
        this.description = builder.description;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

/*	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}*/

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/*public Date getModificationTime() {
		return modificationTime;
	}

	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}*/

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
	
/*	public String getCreatedByUser() {
		return createdByUser;
	}

	public void setCreatedByUser(String createdByUser) {
		this.createdByUser = createdByUser;
	}

	public String getModifiedByUser() {
		return modifiedByUser;
	}

	public void setModifiedByUser(String modifiedByUser) {
		this.modifiedByUser = modifiedByUser;
	}*/

	@Override
	public String toString() {
	        return new ToStringBuilder(this)
	                .append("createdByUser", getCreatedByUser())
	                .append("creationTime", getCreationTime())
	                .append("description", this.description)
	                .append("id", this.id)
	                .append("modifiedByUser", getModifiedByUser())
	                .append("modificationTime", getModificationTime())
	                .append("title", this.title)
	                .append("version", this.version)
	                .toString();
	}
	
	/**
     * This entity is so simple that you don't really need to use the builder pattern
     * (use a constructor instead). I use the builder pattern here because it makes
     * the code a bit more easier to read.
     */
    static class Builder {
        private String description;
        private String title;

        private Builder() {}

        Builder description(String description) {
            this.description = description;
            return this;
        }

        Builder title(String title) {
            this.title = title;
            return this;
        }

        Todo build() {
            Todo build = new Todo(this);

            build.requireValidTitleAndDescription(build.getTitle(), build.getDescription());

            return build;
        }
    }
    
    void update(String newTitle, String newDescription) {
        requireValidTitleAndDescription(newTitle, newDescription);

        this.title = newTitle;
        this.description = newDescription;
    }

    private void requireValidTitleAndDescription(String title, String description) {
        notNull(title, "Title cannot be null.");
        notEmpty(title, "Title cannot be empty.");
        isTrue(title.length() <= MAX_LENGTH_TITLE,
                "The maximum length of the title is <%d> characters.",
                MAX_LENGTH_TITLE
        );

        isTrue((description == null) || (description.length() <= MAX_LENGTH_DESCRIPTION),
                "The maximum length of the description is <%d> characters.",
                MAX_LENGTH_DESCRIPTION
        );
    }

    
}