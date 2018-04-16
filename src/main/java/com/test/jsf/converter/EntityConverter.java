package com.test.jsf.converter;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import org.apache.log4j.Logger;
import com.test.domain.base.BaseEntity;
import com.test.services.base.IVariableTypeService;

@RequestScoped
@ManagedBean(name = "entityConverter")
public class EntityConverter implements javax.faces.convert.Converter, Serializable {

    private static final long serialVersionUID = 1L;

    private static Logger log = Logger.getLogger(EntityConverter.class);

    @ManagedProperty(value = "#{variableTypeService}")
    private IVariableTypeService variableTypeService;

    public void setVariableTypeService(IVariableTypeService variableTypeService) {
        this.variableTypeService = variableTypeService;
    }

    @SuppressWarnings("unchecked")
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        BaseEntity entity;
        if (value == null || "".equals(value)) {
            entity = null;
        } else {
            String[] idAndClass = value.split("_");

            Long id = Long.valueOf(idAndClass[0]);
            @SuppressWarnings("rawtypes")
            Class clazz;
            try {
                clazz = Class.forName(idAndClass[1]);
            } catch (ClassNotFoundException e) {
                throw new ConverterException("Class with name " + idAndClass[1] + " was not found.");
            }

            entity = (BaseEntity) variableTypeService.findById(clazz, id);
            if (entity == null) {
                log.error("There is no entity with id:  " + id + " for class " + clazz);
            }
        }
        return entity;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null || "".equals(value)) {
            return "";
        }
        if (!(value instanceof BaseEntity)) {
            throw new IllegalArgumentException("This converter only handles instances of BaseEntity");
        }
        BaseEntity entity = (BaseEntity) value;
        return entity.getId() == null ? "" : entity.getId().toString() + "_" + entity.getClass().getCanonicalName();
    }
    
}
