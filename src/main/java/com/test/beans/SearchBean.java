package com.test.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.test.domain.Clothing;
import com.test.domain.Footwear;
import com.test.services.IClothingService;
import com.test.services.IFootwearService;

@ManagedBean(name = "searchBean")
@SessionScoped
public class SearchBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value = "#{clothingService}")
	private IClothingService clothingService;
	
	@ManagedProperty(value = "#{footwearService}")
	private IFootwearService footwearService;
	
	private List<Clothing> clothingList;
	private List<Footwear> footwearList;

	public String searchBySport(String strSearch) {
		clothingList = clothingService.getClothingBySport(strSearch);
		setClothingList(clothingService.getClothingBySport(strSearch));
		footwearList = footwearService.getFootwearBySport(strSearch);
		
		return "searchResults";
	}

	public List<Clothing> getClothingList() {
		return clothingList;
	}

	public void setClothingList(List<Clothing> clothingList) {
		this.clothingList = clothingList;
	}

	public List<Footwear> getFootwearList() {
		return footwearList;
	}

	public void setFootwearList(List<Footwear> footwearList) {
		this.footwearList = footwearList;
	}

	public IClothingService getClothingService() {
		return clothingService;
	}

	public void setClothingService(IClothingService clothingService) {
		this.clothingService = clothingService;
	}

	public IFootwearService getFootwearService() {
		return footwearService;
	}

	public void setFootwearService(IFootwearService footwearService) {
		this.footwearService = footwearService;
	}
}
