package com.test.jsf.datatable;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;
import org.springframework.data.domain.Sort.Direction;

public class PaginationConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    private int firstRow;
    
    private int numberOfRows;

    private Map<String, Object> filters;

    private List<String> fetchFields;

    private String sortField;

    private SortOrder ordering;

    public PaginationConfiguration(int firstRow, int numberOfRows, Map<String, Object> filters,
            List<String> fetchFields, String sortField, SortOrder ordering) {
        this.firstRow = firstRow;
        this.numberOfRows = numberOfRows;
        this.filters = filters;
        this.sortField = sortField;
        this.ordering = ordering;
        this.fetchFields = fetchFields;
    }

    public int getFirstRow() {
        return firstRow;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public String getSortField() {
        return sortField;
    }

    public SortOrder getOrdering() {
        return ordering;
    }

    public Map<String, Object> getFilters() {
        return filters;
    }

    public List<String> getFetchFields() {
        return fetchFields;
    }

    public void setFetchFields(List<String> fetchFields) {
        this.fetchFields = fetchFields;
    }

    public boolean isSorted() {
        return ordering != null && sortField != null && sortField.trim().length() != 0;
    }

    public boolean isAscendingSorting() {
        return ordering != null && ordering == SortOrder.ASCENDING;
    }

    public Direction getSortDirection() {
        switch (ordering) {
        case ASCENDING:
            return Direction.ASC;
        case DESCENDING:
            return Direction.DESC;
        default:
            return null;
        }
    }

}
