package com.test.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.test.domain.Souvenir;
import com.test.repositories.SouvenirRepository;
import com.test.services.base.BaseService;

@Service("souvenirService")
public class SouvenirService extends BaseService<Souvenir> implements ISouvenirService{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SouvenirRepository repository;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected JpaRepository<Souvenir, Long> getRepository() {
		return (JpaRepository) repository;
	}
	
	@Override
	public List<Souvenir> getSouvenirByTeamId(int teamId){
		return repository.findByTeamId(teamId);
	}

}
