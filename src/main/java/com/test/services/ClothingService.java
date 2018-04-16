package com.test.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.test.domain.Clothing;
import com.test.repositories.ClothingRepository;
import com.test.services.base.BaseService;

@Service("clothingService")
public class ClothingService extends BaseService<Clothing> implements IClothingService{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ClothingRepository repository;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected JpaRepository<Clothing, Long> getRepository() {
		return (JpaRepository) repository;
	}

	@Override
	public List<Clothing> getClothingByTeamId(int teamId) {
		return repository.findByTeamId(teamId);
	}

	@Override
	public List<Clothing> getClothingBySport(String sport) {
		return repository.findBySport(sport);
	}

}
