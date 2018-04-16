package com.test.services.base;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.test.domain.base.BaseEntity;
import com.test.domain.base.IEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service("variableTypeService")
public class VariableTypeService implements IVariableTypeService, Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("rawtypes")
    public BaseEntity findById(Class<? extends IEntity> entityClass, Long id) {
        List list = em.createQuery("from " + entityClass.getName() + " where id=?").setParameter(1, id).getResultList();
        return list.size() > 0 ? (BaseEntity) list.get(0) : null;
    }

}
