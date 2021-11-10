package com.revature.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.revature.models.HBTopGenre;
import com.revature.models.HBUserAccount;

public interface HBTopGenreDAO extends JpaRepository<HBTopGenre, Integer>{
	
	List<HBTopGenre> findByUser(HBUserAccount user);
	
	void deleteByUser(HBUserAccount user);
	
	@Query("FROM HBTopGenre g where g.genre LIKE :genre")
	Optional<List<HBTopGenre>> findOtherByGenre(@Param("genre") String genre);
}
