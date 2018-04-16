package com.test.services;

import java.util.List;

import com.test.domain.Souvenir;
import com.test.services.base.IService;

public interface ISouvenirService extends IService<Souvenir>{
	public List<Souvenir> getSouvenirByTeamId(int teamId);
}
