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
	@Query("FROM HBMatch m where m.matcherId = :user OR m.matcheeId = :user")
	List<HBMatch> findByMatcherOrMatchee(@Param("user") int userId);
	
	@Query("FROM HBMatch m where (m.matcherId = :user AND m.matcheeId = :other) OR (m.matcheeId = :user AND m.matcherId = :other)")
	HBMatch findByMatchCombination(@Param("user") int userId, @Param("other") int otherId);
}
