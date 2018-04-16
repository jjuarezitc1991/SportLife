package com.test.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.test.domain.base.BaseEntity;

@Entity
@Table(name = "Purchase_Order")
public class PurchaseOrder extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	public PurchaseOrder() {
	}
	
	public PurchaseOrder(String deliveryAddress, double total,
						String deliveryProvider, String creditCardNumber,
						User user) {
		this.deliveryAddress = deliveryAddress;
		this.total = total;
		this.deliveryProvider = deliveryProvider;
		this.creditCardNumber = creditCardNumber;
		this.user = user;
	}
	
	@Column(name = "Delivery_Address", nullable = false)
	private String deliveryAddress;
	
	@Column(name = "Total", nullable = false)
	private double total;
	
	@Column(name = "Delivery_Provider", nullable = false)
	private String deliveryProvider;
	
	@Column(name = "Credit_Card_Number", nullable = true)
	private String creditCardNumber;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "User_Id")
	private User user;

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getDeliveryProvider() {
		return deliveryProvider;
	}

	public void setDeliveryProvider(String deliveryProvider) {
		this.deliveryProvider = deliveryProvider;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
