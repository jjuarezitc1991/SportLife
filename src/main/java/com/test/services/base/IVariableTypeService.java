package com.test.services.base;

import com.test.domain.base.BaseEntity;
import com.test.domain.base.IEntity;

public interface IVariableTypeService {
    BaseEntity findById(Class<? extends IEntity> entityClass, Long id);
}
