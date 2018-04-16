package com.test.beans.base;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.google.common.hash.Hashing;
import com.test.domain.User;
import com.test.domain.base.IEntity;
import com.test.jsf.datatable.PaginationConfiguration;
import com.test.services.base.IService;
import com.test.utils.FacesUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public abstract class BaseBean<T extends IEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    private static Logger log = Logger.getLogger(BaseBean.class.getName());

    private Map<String, Object> filters;

    private Class<T> clazz;

    private T entity;

    private Long objectId;

    private boolean edit = false;

    private LazyDataModel<T> dataModel;

    private DataTable dataTable;

    private IEntity[] selectedEntities;

    public BaseBean(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public T initEntity() {
        if (getObjectId() != null) {
            if (getFormFieldsToFetch() == null) {
                entity = getPersistenceService().findById(getObjectId());
            } else {
                entity = getPersistenceService().findById(getObjectId(), getFormFieldsToFetch());
            }
        } else {
            try {
                entity = getInstance();
            } catch (InstantiationException e) {
                log.error("Unexpected error!", e);
                throw new IllegalStateException("could not instantiate a class, abstract class");
            } catch (IllegalAccessException e) {
                log.error("Unexpected error!", e);
                throw new IllegalStateException("could not instantiate a class, constructor not accessible");
            }
        }
        return entity;
    }

    public T getEntity() {
        return entity != null ? entity : initEntity();
    }

    public String save() {
        try {
            if (entity != null) {
            		if(entity instanceof User) {
            			String hashed = Hashing.sha256()
            			        .hashString(((User)entity).getPassword(), StandardCharsets.UTF_8)
            			        .toString();
            			((User)entity).setPassword(hashed);
            		}
                saveOrUpdate(entity);
                return getViewAfterSave();
            } else {
                log.error("save() method was invoked when when no entity was loaded");
                FacesUtils.error("system.saveError");
            }
        } catch (Throwable e) {
            log.error(String.format("Unexpected error when saving entity %s with id %s", clazz.getName(), entity.getId()), e);
            FacesUtils.error("system.saveError");
        }
        return null;
    }

    public void saveOrUpdate(T entity) {
        if (entity.isTransient()) {
            getPersistenceService().add(entity);
            FacesUtils.info("save.successful");
        } else {
            getPersistenceService().update(entity);
            FacesUtils.info("update.successful");
        }
    }

    public List<T> listAll() {
        return getPersistenceService().list();
    }

    public String getViewAfterSave() {
        return getListViewName();
    }

    public String getNewViewName() {
        return getEditViewName();
    }

    public String getEditViewName() {
        String className = clazz.getSimpleName();
        StringBuilder sb = new StringBuilder(className);
        sb.append("Edit");
        char[] dst = new char[1];
        sb.getChars(0, 1, dst, 0);
        sb.replace(0, 1, new String(dst).toLowerCase());
        return sb.toString();
    }

    public String getListViewName() {
        String className = clazz.getSimpleName();
        StringBuilder sb = new StringBuilder(className);
        char[] dst = new char[1];
        sb.getChars(0, 1, dst, 0);
        sb.replace(0, 1, new String(dst).toLowerCase());
        sb.append("s");
        return sb.toString();
    }

    public void delete(Long id) {
        try {
            log.info(String.format("Deleting entity %s with id = %s", clazz.getName(), id));
            getPersistenceService().delete(id);
            FacesUtils.info("delete.successful");
        } catch (Throwable t) {
            if (t.getCause() instanceof ConstraintViolationException) {
                log.info("delete was unsuccessful because entity is used in the system", t);
                FacesUtils.error("delete.entityUsed");
            } else {
                log.info("unexpected exception when deleting!", t);
                FacesUtils.error("delete.unexpected");
            }
        }
    }

    public void deleteMany() {
        try {
            if (selectedEntities != null && selectedEntities.length > 0) {
                Set<Long> idsToDelete = new HashSet<Long>();
                StringBuilder idsString = new StringBuilder();
                for (IEntity entityToDelete : selectedEntities) {
                    idsToDelete.add((Long) entityToDelete.getId());
                    idsString.append(entityToDelete.getId()).append(" ");
                }
                log.info(String.format("Deleting multiple entities %s with ids = %s", clazz.getName(), idsString.toString()));
                getPersistenceService().deleteMany(idsToDelete);
                FacesUtils.info("delete.successful");
            } else {
                FacesUtils.warn("delete.noSelection");
            }
        } catch (Throwable t) {
            if (t.getCause() instanceof ConstraintViolationException) {
                log.info("delete was unsuccessful because entity is used in the system", t);
                FacesUtils.error("delete.entityUsed");
            } else {
                log.info("unexpected exception when deleting!", t);
                FacesUtils.error("delete.unexpected");
            }
        }
    }

    public Map<String, Object> getFilters() {
        if (filters == null) {
            filters = new HashMap<String, Object>();
        }
        return filters;
    }

    public void clean() {
        filters = new HashMap<String, Object>();
    }

    public void resetFormEntity() {
        entity = null;
        entity = getEntity();
    }

    public T getInstance() throws InstantiationException, IllegalAccessException {
        return clazz.newInstance();
    }

    protected abstract IService<T> getPersistenceService();

    protected List<String> getListFieldsToFetch() {
        return null;
    }

    protected List<String> getFormFieldsToFetch() {
        return null;
    }

    public LazyDataModel<T> getLazyDataModel() {
        if (dataModel == null) {
            dataModel = new LazyDataModel<T>() {

                private static final long serialVersionUID = 1L;

                private Integer rowCount;

                private Integer rowIndex;

                @Override
                public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> loadingFilters) {
                    Map<String, Object> copyOfFilters = new HashMap<String, Object>();
                    copyOfFilters.putAll(getFilters());

                    setRowCount((int) getPersistenceService().count(
                            new PaginationConfiguration(first, pageSize, copyOfFilters, getListFieldsToFetch(), sortField, sortOrder)));
                    if (getRowCount() > 0) {
                        copyOfFilters = new HashMap<String, Object>();
                        copyOfFilters.putAll(getFilters());
                        return getPersistenceService().list(
                                new PaginationConfiguration(first, pageSize, copyOfFilters, getListFieldsToFetch(), sortField, sortOrder));
                    } else {
                        return null;
                    }
                }

                @Override
                public T getRowData(String rowKey) {
                    return getPersistenceService().findById(Long.valueOf(rowKey));
                }

                @Override
                public Object getRowKey(T object) {
                    return object.getId();
                }

                @Override
                public void setRowIndex(int rowIndex) {
                    if (rowIndex == -1 || getPageSize() == 0) {
                        this.rowIndex = rowIndex;
                    } else {
                        this.rowIndex = rowIndex % getPageSize();
                    }
                }

                @SuppressWarnings("unchecked")
                @Override
                public T getRowData() {
                    return ((List<T>) getWrappedData()).get(rowIndex);
                }

                @SuppressWarnings({ "unchecked" })
                @Override
                public boolean isRowAvailable() {
                    if (getWrappedData() == null) {
                        return false;
                    }

                    return rowIndex >= 0 && rowIndex < ((List<T>) getWrappedData()).size();
                }

                @Override
                public int getRowIndex() {
                    return this.rowIndex;
                }

                @Override
                public void setRowCount(int rowCount) {
                    this.rowCount = rowCount;
                }

                @Override
                public int getRowCount() {
                    if (rowCount == null) {
                        rowCount = (int) getPersistenceService().count();
                    }
                    return rowCount;
                }

            };
        }
        return dataModel;
    }

    public String search() {
        dataTable.reset();
        return null;
    }

    public DataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(DataTable dataTable) {
        this.dataTable = dataTable;
    }

    public IEntity[] getSelectedEntities() {
        if (selectedEntities != null) {
            return Arrays.copyOf(selectedEntities, selectedEntities.length);
        } else {
            return null;
        }
    }

    public void setSelectedEntities(IEntity[] selectedEntities) {
        if (selectedEntities != null) {
            this.selectedEntities = Arrays.copyOf(selectedEntities, selectedEntities.length);
        }
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

}
