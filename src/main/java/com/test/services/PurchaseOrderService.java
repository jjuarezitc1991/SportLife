package com.test.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.test.domain.PurchaseOrder;
import com.test.repositories.PurchaseOrderRepository;
import com.test.services.base.BaseService;

@Service("purchaseOrderService")
public class PurchaseOrderService extends BaseService<PurchaseOrder> implements IPurchaseOrderService {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private PurchaseOrderRepository repository;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected JpaRepository<PurchaseOrder, Long> getRepository() {
		return (JpaRepository) repository;
	}

}
