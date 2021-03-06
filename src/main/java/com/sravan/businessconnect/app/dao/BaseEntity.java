package com.sravan.businessconnect.app.dao;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
 
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseEntity {
 
	/*@CreatedDate : This identifies the field whose value is set 
	when the entity is persisted to the database for the first time
    */
	@Column(name = "creation_time", nullable = false)
	@Temporal(TemporalType.DATE)
    @CreatedDate
    private Date creationTime;
	
	/*@LastModifiedDate : This identifies the field whose value is set when the entity is persisted 
	for the first time and updated when the information of the entity is updated.*/
    @Column(name = "modification_time")
	@Temporal(TemporalType.DATE)
    @LastModifiedDate
    private Date modificationTime;
    
    
    @Column(name = "created_by_user", nullable = false)
    @CreatedBy
    private String createdByUser;
    
    @Column(name = "modified_by_user", nullable = false)
    @LastModifiedBy
    private String modifiedByUser;
    
    public Date getModificationTime() {
		return modificationTime;
	}

	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}
	
	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	
	public String getCreatedByUser() {
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
	}
	
}