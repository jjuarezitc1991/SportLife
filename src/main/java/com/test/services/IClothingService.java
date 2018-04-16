package com.test.services;

import java.util.List;

import com.test.domain.Clothing;
import com.test.services.base.IService;

public interface IClothingService extends IService<Clothing>{
	public List<Clothing> getClothingByTeamId(int teamId);
	public List<Clothing> getClothingBySport(String sport);
}
