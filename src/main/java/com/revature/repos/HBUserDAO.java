package com.revature.repos;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.revature.models.HBUserAccount;

public interface HBUserDAO extends JpaRepository<HBUserAccount, Integer>
{
	HBUserAccount findByUsernameIgnoreCase(@Param("username") String username);
}