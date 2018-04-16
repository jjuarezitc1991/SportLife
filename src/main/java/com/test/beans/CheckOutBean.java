package com.test.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.test.domain.PurchaseOrder;
import com.test.domain.User;
import com.test.services.IPurchaseOrderService;

@ManagedBean(name = "checkOutBean")
@ViewScoped
public class CheckOutBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value = "#{purchaseOrderService}")
	private IPurchaseOrderService purchaseOrderService;
	
	private String paymentType;
	private Double total;
	private String deliveryAddress;
	private String deliveryService;
	private String creditCardNumber;
	private String nip;
	
	@ManagedProperty("#{cartBean}")
    private CartBean cartBean; // +setter (no getter!)
	
	@PostConstruct
	public void init() {
		creditCardNumber = null;
		nip = null;
		
		FacesContext context = FacesContext.getCurrentInstance();
		String value = context.getApplication().evaluateExpressionGet(context, "#{cartBean['total']}", String.class);
		this.total = Double.parseDouble(value);
		
		deliveryAddress = SessionPreferences.getLoggedInUser().getAddress();
	}
	
	public String checkOut() {
		User user = SessionPreferences.getLoggedInUser();
		PurchaseOrder pO = new PurchaseOrder(deliveryAddress, total, 
									deliveryService, creditCardNumber,
									user);
		purchaseOrderService.add(pO);
		cartBean.clearOut();
		return "confirmation";
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getDeliveryService() {
		return deliveryService;
	}

	public void setDeliveryService(String deliveryService) {
		this.deliveryService = deliveryService;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public IPurchaseOrderService getPurchaseOrderService() {
		return purchaseOrderService;
	}

	public void setPurchaseOrderService(IPurchaseOrderService purchaseOrderService) {
		this.purchaseOrderService = purchaseOrderService;
	}

	public void setCartBean(CartBean cartBean) {
		this.cartBean = cartBean;
	}
}
