package com.test.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.test.domain.base.BaseEntity;

@Entity(name = "Footwear")
@DiscriminatorValue("Footwear")
public class Footwear extends BaseEntity {

private static final long serialVersionUID = 1L;
	
	@Column(name = "Name")
	private String name;
	
	@Column(name = "Size")
	private int size;
	
	@Column(name = "Color")
	private String color;
	
	@Column(name = "Description")
	private String description;
	
	@Column(name = "Image")
	private String image;
	
	@Column(name = "Stock")
	private Integer stock;
	
	@Column(name = "Price")
	private Double price;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Team_Id")
	private Team team;
	
	@Column(name = "Sport")
	private String sport;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
