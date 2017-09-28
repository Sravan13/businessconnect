package com.sravan.businessconnect.app.dao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the category database table.
 * 
 */
@Entity
@Table(name="category")
//@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="cat_id")
	private int catId;

	@Column(name="cat_display_name")
	private String catDisplayName;

	@Column(name="cat_name")
	private String catName;

	@Column(name="cat_parent_id")
	private int catParentId;


	@Temporal(TemporalType.DATE)
	@Column(name="creation_date")
	private Date creationDate;


	@Temporal(TemporalType.DATE)
	@Column(name="modified_date")
	private Date modifiedDate;

	public Category() {
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}


	public String getCatDisplayName() {
		return catDisplayName;
	}

	public void setCatDisplayName(String catDisplayName) {
		this.catDisplayName = catDisplayName;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}


	public int getCatParentId() {
		return catParentId;
	}

	public void setCatParentId(int catParentId) {
		this.catParentId = catParentId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}


	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}