package com.test.services.base;

import java.beans.Introspector;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.test.customrepository.GenericRepository;
import com.test.domain.base.BaseEntity;
import com.test.domain.base.IEntity;
import com.test.jsf.datatable.PaginationConfiguration;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.BooleanPath;
import com.mysema.query.types.path.DatePath;
import com.mysema.query.types.path.EnumPath;
import com.mysema.query.types.path.ListPath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.PathBuilder;
import com.mysema.query.types.path.StringPath;

@Transactional(readOnly = true)
public abstract class BaseService<T extends IEntity> implements IService<T>, Serializable {

    private static final long serialVersionUID = 1L;

    protected final Class<? extends IEntity> entityClass;

    @PersistenceContext
    protected EntityManager em;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public BaseService() {
        Class clazz = getClass();
        while (!(clazz.getGenericSuperclass() instanceof ParameterizedType)) {
            clazz = clazz.getSuperclass();
        }
        Object o = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];

        if (o instanceof TypeVariable) {
            this.entityClass = (Class<T>) ((TypeVariable) o).getBounds()[0];
        } else {
            this.entityClass = (Class<T>) o;
        }
    }

    protected abstract JpaRepository<T, Long> getRepository();

    @Override
    @Transactional(readOnly = false)
    public void add(T entity) {
        getRepository().save(entity);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(T entity) {
        getRepository().save(entity);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(T entity) {
        getRepository().delete(entity);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        getRepository().delete(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteMany(Iterable<Long> ids) {
        for (Long id : ids) {
            getRepository().delete(id);
        }
    }

    @Override
    public T findById(Long id) {
        return getRepository().findOne(id);
    }

    @Override
    public T findById(Long id, List<String> fetchFields) {
        return ((GenericRepository<T, Long>) getRepository()).findOne(id, fetchFields);
    }

    @Override
    public List<T> list() {
        return getRepository().findAll();
    }

    @Override
    public long count() {
        return getRepository().count();
    }

    @Override
    public List<T> list(final PaginationConfiguration config) {
        Predicate predicate = getPredicate(config);
        Pageable pageable = new PageRequest(config.getFirstRow() / config.getNumberOfRows(), config.getNumberOfRows(),
                config.getSortField() != null ? new Sort(new Sort.Order(config.getSortDirection(), config.getSortField())) : null);
        return ((GenericRepository<T, Long>) getRepository()).findAll(predicate, pageable, config.getFetchFields()).getContent();
    }

    @Override
    public long count(PaginationConfiguration config) {
        Predicate predicate = getPredicate(config);
        return ((GenericRepository<T, Long>) getRepository()).count(predicate);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected Predicate getPredicate(PaginationConfiguration config) {

        PathBuilder<T> entityPath = new PathBuilder(entityClass, getAliasName(entityClass));
        BooleanExpression predicate = null;

        Map<String, Object> filters = config.getFilters();
        if (filters != null) {
        	
            List<String> filtersToRemove = new ArrayList<String>();
            predicate = processNonStandardFilters(filters, filtersToRemove, entityPath);
            removeUsedFilters(filtersToRemove, filters);
            if (!filters.isEmpty()) {
                for (Map.Entry<String, Object> entry : filters.entrySet()) {
                    String key = entry.getKey();
                    Object filter = entry.getValue();
                    if (filter != null) {


                        if (key.contains("fromRange-")) {

                            String parsedKey = key.substring(10);

                            if (filter instanceof Number) {
                                NumberPath path = createNumberPath(entityPath, parsedKey, filter);
                                predicate = and(predicate, path.goe((Number) filter));
                            } else if (filter instanceof Date) {
                                DatePath path = entityPath.getDate(parsedKey, Date.class);
                                predicate = and(predicate, path.goe((Date) filter));
                            }
                        } else if (key.contains("toRange-")) {
                            // CHECKSTYLE:OFF
                            String parsedKey = key.substring(8);
                            // CHECKSTYLE:ON
                            if (filter instanceof Number) {
                                NumberPath path = createNumberPath(entityPath, parsedKey, filter);
                                predicate = and(predicate, path.loe((Number) filter));
                            } else if (filter instanceof Date) {
                                DatePath path = entityPath.getDate(parsedKey, Date.class);
                                predicate = and(predicate, path.loe((Date) filter));
                            }
                        } else if (key.contains("list-")) {
                            // CHECKSTYLE:OFF
                            // if searching elements from list
                            String parsedKey = key.substring(5);
                            // CHECKSTYLE:ON
                            ListPath path = entityPath.getList(parsedKey, filter.getClass());
                            predicate = and(predicate, path.contains(filter));
                        } else { // if not ranged search
                            if (filter instanceof String) {
                                StringPath path = entityPath.getString(key);
                                String filterString = (String) filter;
                                predicate = and(predicate, path.startsWithIgnoreCase(filterString));
                            } else if (filter instanceof Date) {
                                DatePath path = entityPath.getDate(key, Date.class);
                                predicate = and(predicate, path.eq(filter));
                            } else if (filter instanceof Number) {
                                NumberPath path = createNumberPath(entityPath, key, filter);
                                predicate = and(predicate, path.eq(filter));
                            } else if (filter instanceof Boolean) {
                                BooleanPath path = entityPath.getBoolean(key);
                                predicate = and(predicate, path.eq((Boolean) filter));
                            } else if (filter instanceof Enum) {
                                EnumPath path = entityPath.getEnum(key, Enum.class);
                                predicate = and(predicate, path.eq(filter));
                            } else if (BaseEntity.class.isAssignableFrom(filter.getClass())) {
                                PathBuilder path = entityPath.get(key);
                                predicate = and(predicate, path.eq(filter));
                            }
                        }

                    }
                }
            }
        }
        return predicate;
    }

    protected BooleanExpression processNonStandardFilters(Map<String, Object> filters, List<String> filtersToRemove,
            @SuppressWarnings("rawtypes") PathBuilder pathBuilder) {
        return null;
    }

    private Map<String, Object> removeUsedFilters(List<String> filtersToRemove, Map<String, Object> filtersMap) {
        for (String key : filtersToRemove) {
            filtersMap.remove(key);
        }
        return filtersMap;
    }

    private String getAliasName(@SuppressWarnings("rawtypes") Class clazz) {
        return Introspector.decapitalize(clazz.getSimpleName());
    }

    private BooleanExpression and(BooleanExpression old, BooleanExpression newPredidcate) {
        if (old != null) {
            return old.and(newPredidcate);
        } else {
            return newPredidcate;
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private NumberPath createNumberPath(PathBuilder entityPath, String key, Object filter) {
        if (filter instanceof BigDecimal) {
            return entityPath.getNumber(key, BigDecimal.class);
        } else if (filter instanceof Long) {
            return entityPath.getNumber(key, Long.class);
        } else if (filter instanceof Integer) {
            return entityPath.getNumber(key, Integer.class);
        } else if (filter instanceof Double) {
            return entityPath.getNumber(key, Double.class);
        } else if (filter instanceof Float) {
            return entityPath.getNumber(key, Float.class);
        } else if (filter instanceof Byte) {
            return entityPath.getNumber(key, Byte.class);
        } else if (filter instanceof Short) {
            return entityPath.getNumber(key, Short.class);
        } else {
            throw new IllegalStateException("Unknown number type in search filter. Supported type: BigDecimal, Long, Integer, Double, Float, Byte, Short");
        }
    }
}
