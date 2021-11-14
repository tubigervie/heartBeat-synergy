package com.revature.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.revature.models.HBMatch;
import com.revature.models.HBUserAccount;

@Repository
public interface HBMatchDAO extends JpaRepository<HBMatch, Integer> 
{
	@Query("FROM HBMatch m where m.matcher = :user OR m.matchee = :user")
	List<HBMatch> findByMatcherOrMatchee(@Param("user") HBUserAccount user);
	
	@Query("FROM HBMatch m where (m.matcher = :user AND m.matchee = :other) OR (m.matchee = :user AND m.matcher = :other)")
	HBMatch findByMatchCombination(@Param("user") HBUserAccount user, @Param("other") HBUserAccount other);
}
