package com.revature.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.revature.models.HBUserAccount;

public interface HBUserDAO extends JpaRepository<HBUserAccount, Integer>
{
	@Query("FROM HBUserAccount a WHERE a.username LIKE :username")
	Optional<HBUserAccount> findAccountByUsername(@Param("username") String username);
}