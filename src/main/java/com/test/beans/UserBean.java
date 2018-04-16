package com.test.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.test.beans.base.BaseBean;
import com.test.domain.User;
import com.test.services.IUserService;
import com.test.services.base.IService;

@ManagedBean(name = "userBean")
@ViewScoped
public class UserBean extends BaseBean<User>{
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value = "#{userService}")
	private IUserService userService;
	
	public UserBean() {
		super(User.class);
	}

	@Override
	protected IService<User> getPersistenceService() {
		return userService;
	}
	
	@Override
	public String getViewAfterSave() {
        return "loginShop";
    }
	
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

}
