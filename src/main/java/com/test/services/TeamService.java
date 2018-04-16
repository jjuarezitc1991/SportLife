package com.test.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.test.domain.Team;
import com.test.repositories.TeamRepository;
import com.test.services.base.BaseService;

@Service("teamService")
public class TeamService extends BaseService<Team> implements ITeamService {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private TeamRepository repository;
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected JpaRepository<Team, Long> getRepository() {
		return (JpaRepository) repository;
	}

}
