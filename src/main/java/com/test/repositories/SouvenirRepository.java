package com.test.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.test.customrepository.GenericRepository;
import com.test.domain.Souvenir;

public interface SouvenirRepository extends GenericRepository<Souvenir, Long>{
	@Query(value="SELECT * FROM Souvenir where Team_Id = :idTeam", nativeQuery=true)
	List<Souvenir> findByTeamId(@Param("idTeam") int idTeam);
}
