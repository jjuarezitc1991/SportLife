package com.test.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.test.customrepository.GenericRepository;
import com.test.domain.Footwear;

public interface FootwearRepository extends GenericRepository<Footwear, Long> {
	@Query(value = "SELECT * FROM Footwear where Team_Id = :idTeam", nativeQuery = true)
	List<Footwear> findByTeamId(@Param("idTeam") int idTeam);
	
	@Query(value = "SELECT * FROM Footwear where Sport = :sport", nativeQuery = true)
	List<Footwear> findBySport(@Param("sport") String sport);
}
