package com.test.jsf.composite;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;

import com.test.beans.base.BaseBean;
import com.test.domain.base.IEntity;

@FacesComponent(value = "formField")
public class FormFieldCompositeComponent extends BaseBeanBasedCompositeComponent {

    private Boolean edit;

    public boolean isEditMode() {
        if (edit == null) {
            edit = (String) getAttributes().get("edit") != null ? Boolean.valueOf((String) getAttributes().get("edit")) : null;
            if (edit == null) { 
                UIComponent parent = getCompositeComponentParent(this);
                if (parent != null) {
                    edit = (String) parent.getAttributes().get("edit") != null ? Boolean.valueOf((String) parent.getAttributes().get("edit")) : null;
                }
            }
            if (edit == null) { 
                BaseBean<? extends IEntity> backingBean = getBackingBeanFromParentOrCurrent();
                if (backingBean != null) {
                    edit = backingBean.isEdit();
                } else {
                    throw new IllegalStateException(
                            "No edit flag was set in parent or current composite component and no backing bean in parent or curret component!");
                }
            }
        }
        return edit;
    }

}
