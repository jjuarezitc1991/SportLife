package com.test.jsf.composite;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;

import com.test.beans.base.BaseBean;
import com.test.domain.base.IEntity;
import com.test.utils.FacesUtils;

public class BaseBeanBasedCompositeComponent extends UINamingContainer {

    private static final String BOOLEAN_TRUE_STRING = "true";

    private BaseBean<? extends IEntity> backingBean;

    private IEntity entity;

    @SuppressWarnings("unchecked")
    public BaseBean<? extends IEntity> getBackingBeanFromParentOrCurrent() {
        if (backingBean == null) {
            UIComponent parent = getCompositeComponentParent(this);
            if (parent != null) {
                backingBean = (BaseBean<? extends IEntity>) parent.getAttributes().get("backingBean");
            }
            if (backingBean == null) {
                backingBean = (BaseBean<? extends IEntity>) getAttributes().get("backingBean");
            }
            if (backingBean == null) {
                throw new IllegalStateException("No backing bean was set in parent or current composite component!");
            }
        }
        return backingBean;
    }

    public IEntity getEntityFromBackingBeanOrAttribute() {
        if (entity == null) {
            entity = (IEntity) getAttributes().get("entity");
            if (entity == null) {
                entity = getBackingBeanFromParentOrCurrent().getEntity();
            }
        }
        return entity;
    }

    public String getDatePattern() {
        if (getAttributes().get("time").equals(BOOLEAN_TRUE_STRING)) {
            return FacesUtils.getMessage("SportLife.dateTimeFormat");
        } else {
            return FacesUtils.getMessage("SportLife.dateFormat");
        }
    }

    public boolean isText(String fieldName) throws NoSuchFieldException {
        Field field = getEntityFromBackingBeanOrAttribute().getClass().getDeclaredField(fieldName);
        return field.getType() == String.class;
    }

    public boolean isBoolean(String fieldName) throws NoSuchFieldException {
        Field field = getEntityFromBackingBeanOrAttribute().getClass().getDeclaredField(fieldName);
        Class<?> type = field.getType();
        return type == Boolean.class || (type.isPrimitive() && type.getName().equals("boolean"));
    }

    public boolean isDate(String fieldName) throws NoSuchFieldException {
        Field field = getEntityFromBackingBeanOrAttribute().getClass().getDeclaredField(fieldName);
        return field.getType() == Date.class;
    }

    public boolean isEnum(String fieldName) throws NoSuchFieldException {
        Field field = getEntityFromBackingBeanOrAttribute().getClass().getDeclaredField(fieldName);
        return field.getType().isEnum();
    }

    public boolean isInteger(String fieldName) throws NoSuchFieldException {
        Field field = getEntityFromBackingBeanOrAttribute().getClass().getDeclaredField(fieldName);
        Class<?> type = field.getType();
        return type == Integer.class || (type.isPrimitive() && type.getName().equals("int"));
    }

    public boolean isLong(String fieldName) throws NoSuchFieldException {
        Field field = getEntityFromBackingBeanOrAttribute().getClass().getDeclaredField(fieldName);
        Class<?> type = field.getType();
        return type == Long.class || (type.isPrimitive() && type.getName().equals("long"));
    }

    public boolean isByte(String fieldName) throws NoSuchFieldException {
        Field field = getEntityFromBackingBeanOrAttribute().getClass().getDeclaredField(fieldName);
        Class<?> type = field.getType();
        return type == Byte.class || (type.isPrimitive() && type.getName().equals("byte"));
    }

    public boolean isShort(String fieldName) throws NoSuchFieldException {
        Field field = getEntityFromBackingBeanOrAttribute().getClass().getDeclaredField(fieldName);
        Class<?> type = field.getType();
        return type == Short.class || (type.isPrimitive() && type.getName().equals("short"));
    }

    public boolean isDouble(String fieldName) throws NoSuchFieldException {
        Field field = getEntityFromBackingBeanOrAttribute().getClass().getDeclaredField(fieldName);
        Class<?> type = field.getType();
        return type == Double.class || (type.isPrimitive() && type.getName().equals("double"));
    }

    public boolean isFloat(String fieldName) throws NoSuchFieldException {
        Field field = getEntityFromBackingBeanOrAttribute().getClass().getDeclaredField(fieldName);
        Class<?> type = field.getType();
        return type == Float.class || (type.isPrimitive() && type.getName().equals("float"));
    }

    public boolean isBigDecimal(String fieldName) throws NoSuchFieldException {
        Field field = getEntityFromBackingBeanOrAttribute().getClass().getDeclaredField(fieldName);
        return field.getType() == BigDecimal.class;
    }

    public boolean isEntity(String fieldName) throws NoSuchFieldException {
        Field field = getEntityFromBackingBeanOrAttribute().getClass().getDeclaredField(fieldName);
        return IEntity.class.isAssignableFrom(field.getType());
    }

    public boolean isList(String fieldName) throws NoSuchFieldException {
        Field field = getEntityFromBackingBeanOrAttribute().getClass().getDeclaredField(fieldName);
        Class<?> type = field.getType();
        return type == List.class || type == Set.class;
    }

    public Object[] getEnumConstants(String fieldName) throws NoSuchFieldException {
        Field field = getEntityFromBackingBeanOrAttribute().getClass().getDeclaredField(fieldName);
        if (field != null && field.getType().isEnum()) {
            return field.getType().getEnumConstants();
        }
        throw new IllegalStateException("No field with name '" + fieldName + "' was found");
    }

}
