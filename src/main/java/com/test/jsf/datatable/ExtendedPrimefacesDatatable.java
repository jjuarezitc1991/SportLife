package com.test.jsf.datatable;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.component.FacesComponent;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;

@FacesComponent(value = "ExtendedPrimefacesDatatable")
public class ExtendedPrimefacesDatatable extends DataTable {

    @Override
    public String resolveStaticField(ValueExpression expression) {
        if (expression != null) {
            FacesContext context = getFacesContext();
            ELContext eLContext = context.getELContext();

            return (String) expression.getValue(eLContext);
        } else {
            return null;
        }
    }

}
