package com.test.jsf.composite;

import java.util.Map;

import javax.faces.component.FacesComponent;

@FacesComponent(value = "searchField")
public class SearchFieldCompositeComponent extends BaseBeanBasedCompositeComponent {

    public Map<String, Object> getFilters() {
        return super.getBackingBeanFromParentOrCurrent().getFilters();
    }

    public String getFromRangeSearchFilterName() {
        return "fromRange-" + getAttributes().get("field");
    }

    public String getToRangeSearchFilterName() {
        return "toRange-" + getAttributes().get("field");
    }

    public String getFromRangeSearchFilterNameFromParent() {
        return "fromRange-" + getCompositeComponentParent(this).getAttributes().get("field");
    }

    public String getToRangeSearchFilterNameFromParent() {
        return "toRange-" + getCompositeComponentParent(this).getAttributes().get("field");
    }

}
