package com.test.services;

import java.util.List;

import com.test.domain.Footwear;
import com.test.services.base.IService;

public interface IFootwearService extends IService<Footwear>{
	public List<Footwear> getFootwearByTeamId(int teamId);
	public List<Footwear> getFootwearBySport(String sport);
}
