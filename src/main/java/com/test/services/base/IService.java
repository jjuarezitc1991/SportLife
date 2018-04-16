package com.test.services.base;

import java.util.List;

import com.test.domain.base.IEntity;
import com.test.jsf.datatable.PaginationConfiguration;

public interface IService<T extends IEntity> {

    void add(T entity);

    void update(T entity);

    void delete(T entity);

    void delete(Long id);

    void deleteMany(Iterable<Long> ids);

    T findById(Long id);

    T findById(Long id, List<String> fetchFields);

    long count();

    long count(PaginationConfiguration config);

    List<T> list();

    List<T> list(PaginationConfiguration config);

}
