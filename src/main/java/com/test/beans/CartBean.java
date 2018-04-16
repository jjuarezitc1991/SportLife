package com.test.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.test.domain.Clothing;
import com.test.domain.Footwear;
import com.test.domain.Souvenir;
import com.test.services.IClothingService;
import com.test.services.IFootwearService;
import com.test.services.ISouvenirService;

@ManagedBean
@SessionScoped
public class CartBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value = "#{clothingService}")
	private IClothingService clothingService;
	
	@ManagedProperty(value = "#{footwearService}")
	private IFootwearService footwearService;
	
	@ManagedProperty(value = "#{souvenirService}")
	private ISouvenirService souvenirService;
	
	List<Clothing> clothingList = new ArrayList<Clothing>();
	List<Footwear> footwearList = new ArrayList<Footwear>();
	List<Souvenir> souvenirList = new ArrayList<Souvenir>();
	
	private double total;
	private int totalArticles;
	
	public int getTotalArticles() {
		this.totalArticles = 0;
		
		for(Clothing clothing:clothingList) {
			totalArticles = totalArticles + clothing.getStock();
		}
		
		for(Footwear footwear:footwearList) {
			totalArticles = totalArticles + footwear.getStock();
		}
		
		for(Souvenir souvenir:souvenirList) {
			totalArticles = totalArticles + souvenir.getStock();
		}
		
		return totalArticles;
	}
	
	public void setTotalArticles(int totalArticles) {
		this.totalArticles = totalArticles;
	}
	
	private double getTotalShoppingCart() {
		double total = 0;
		
		for(Clothing clothing:clothingList) {
			total = total + (clothing.getPrice() * clothing.getStock());
		}
		
		for(Footwear footwear:footwearList) {
			total = total + (footwear.getPrice() * footwear.getStock());
		}
		
		for(Souvenir souvenir:souvenirList) {
			total = total + (souvenir.getPrice() * souvenir.getStock());
		}
		return total;
	}
	
	public double getTotal() {
		total = getTotalShoppingCart();
		return total;
	}
	
	public void setTotal(double total) {
		this.total = total;
	}
	
	public String addClothing(Long id, String strCantidad) {
		for(Clothing clothing:clothingList) {
			if(clothing.getId() == id) {
				clothing.setStock(clothing.getStock() + Integer.parseInt(strCantidad));
				return "shoppingCart";
			}
		}
		Clothing clothing = clothingService.findById(id);
		clothing.setStock(Integer.parseInt(strCantidad));
		
		clothingList.add(clothing);
		return "shoppingCart";
	}
	
	public String removeClothing(Long id) {
		for (Iterator<Clothing> iterator = clothingList.iterator(); iterator.hasNext(); ) {
			Clothing clothing = iterator.next();
			if(clothing.getId() == id){
				iterator.remove();
			}
		}
		return "shoppingCart";
	}
	
	public String addFootwear(Long id, String strCantidad) {
		Footwear footwear = footwearService.findById(id);
		footwear.setStock(Integer.parseInt(strCantidad));
		
		footwearList.add(footwear);
		
		return "shoppingCart";
	}
	
	public String removeFootwear(Long id) {
		for (Iterator<Footwear> iterator = footwearList.iterator(); iterator.hasNext(); ) {
			Footwear footwear = iterator.next();
			if(footwear.getId() == id){
				iterator.remove();
				
			}
		}
		return "shoppingCart";
	}
	
	public String addSouvenir(Long id, String strCantidad) {
		Souvenir souvenir = souvenirService.findById(id);
		souvenir.setStock(Integer.parseInt(strCantidad));
		
		souvenirList.add(souvenir);
	    
		return "shoppingCart";
	}
	
	public String removeSouvenir(Long id) {
		for (Iterator<Souvenir> iterator = souvenirList.iterator(); iterator.hasNext(); ) {
			Souvenir souvenir = iterator.next();
			if(souvenir.getId() == id){
				iterator.remove();
				
			}
		}
		return "shoppingCart";
	}
	
	public void clearOut() {
		this.clothingList.clear();
		this.footwearList.clear();
		this.souvenirList.clear();
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

	public ISouvenirService getSouvenirService() {
		return souvenirService;
	}

	public void setSouvenirService(ISouvenirService souvenirService) {
		this.souvenirService = souvenirService;
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

	public List<Souvenir> getSouvenirList() {
		return souvenirList;
	}

	public void setSouvenirList(List<Souvenir> souvenirList) {
		this.souvenirList = souvenirList;
	}
}
