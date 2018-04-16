package com.test.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.test.beans.base.BaseBean;
import com.test.domain.PurchaseOrder;
import com.test.services.IPurchaseOrderService;
import com.test.services.base.IService;

@ManagedBean(name = "purchaseOrderBean")
@ViewScoped
public class PurchaseOrderBean extends BaseBean<PurchaseOrder>{
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value = "#{purchaseOrderService}")
	private IPurchaseOrderService purchaseOrderService;
	
	public PurchaseOrderBean() {
		super(PurchaseOrder.class);
	}

	@Override
	protected IService<PurchaseOrder> getPersistenceService() {
		return purchaseOrderService;
	}
	
	public void setPurchaseOrderService(IPurchaseOrderService purchaseOrderService) {
		this.purchaseOrderService = purchaseOrderService;
		
	}

}
