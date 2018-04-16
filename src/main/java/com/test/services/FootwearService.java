package com.test.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.test.domain.Footwear;
import com.test.repositories.FootwearRepository;
import com.test.services.base.BaseService;

@Service("footwearService")
public class FootwearService extends BaseService<Footwear> implements IFootwearService{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private FootwearRepository repository;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected JpaRepository<Footwear, Long> getRepository() {
		return (JpaRepository) repository;
	}

	@Override
	public List<Footwear> getFootwearByTeamId(int teamId) {
		return repository.findByTeamId(teamId);
	}

	@Override
	public List<Footwear> getFootwearBySport(String sport) {
		return repository.findBySport(sport);
	}

}
