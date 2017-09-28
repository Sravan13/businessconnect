package com.sravan.businessconnect.sec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sravan.businessconnect.sec.dao.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{

	User findByEmail(String email);

}