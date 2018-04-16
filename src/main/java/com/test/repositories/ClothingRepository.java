package com.test.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.test.customrepository.GenericRepository;
import com.test.domain.Clothing;

public interface ClothingRepository extends GenericRepository<Clothing, Long>{
	@Query(value = "SELECT * FROM Clothing where Team_Id = :idTeam", nativeQuery = true)
	List<Clothing> findByTeamId(@Param("idTeam") int idTeam);
	
	@Query(value = "SELECT * FROM Clothing where Sport = :sport", nativeQuery = true)
	List<Clothing> findBySport(@Param("sport") String sport);
}
