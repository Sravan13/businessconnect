package com.sravan.businessconnect.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sravan.businessconnect.app.dao.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
