package com.revature.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.HBUserAccount;

public interface HBUserDAO extends JpaRepository<HBUserAccount, Integer>{
}