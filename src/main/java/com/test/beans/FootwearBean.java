package com.test.beans;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.test.beans.base.BaseBean;
import com.test.domain.Footwear;
import com.test.services.IFootwearService;
import com.test.services.base.IService;
import com.test.utils.URLUtils;

@ManagedBean(name = "footwearBean")
public class FootwearBean extends BaseBean<Footwear>{

	private static final long serialVersionUID = 1L;
	
	private List<Footwear> list;
	
	@ManagedProperty(value = "#{footwearService}")
	private IFootwearService footwearService;
	
	public FootwearBean() {
		super(Footwear.class);
	}
	
	@PostConstruct
	public void init() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
		String uri = request.getRequestURI();
		int teamId = URLUtils.getTeamNameInURI(uri);
		list = footwearService.getFootwearByTeamId(teamId);
	}

	@Override
	protected IService<Footwear> getPersistenceService() {
		return footwearService;
	}
	
	public void setFootwearService(IFootwearService footwearService) {
		this.footwearService = footwearService;
	}
	
	public List<Footwear> getList(){
		return list;
	}
	
	public void setList(List<Footwear> list) {
		this.list = list;
	}
	
	@Override
    protected List<String> getListFieldsToFetch() {
        return Arrays.asList("team");
    }

    @Override
    protected List<String> getFormFieldsToFetch() {
        return Arrays.asList("team");
    }
}
