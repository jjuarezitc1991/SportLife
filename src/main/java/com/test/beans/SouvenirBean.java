package com.test.beans;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.test.beans.base.BaseBean;
import com.test.domain.Souvenir;
import com.test.services.ISouvenirService;
import com.test.services.base.IService;
import com.test.utils.URLUtils;

@ManagedBean(name = "souvenirBean")
@ViewScoped
public class SouvenirBean extends BaseBean<Souvenir>{

	private static final long serialVersionUID = 1L;
	private List<Souvenir> list;
	
	@ManagedProperty(value = "#{souvenirService}")
	private ISouvenirService souvenirService;
	
	public SouvenirBean() {
		super(Souvenir.class);
	}
	
	@PostConstruct
	public void init() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
		String uri = request.getRequestURI();
		int teamId = URLUtils.getTeamNameInURI(uri);
		list = souvenirService.getSouvenirByTeamId(teamId);
	}

	@Override
	protected IService<Souvenir> getPersistenceService() {
		return souvenirService;
	}

	public void setSouvenirService(ISouvenirService souvenirService) {
		this.souvenirService = souvenirService;
	}
	
	public List<Souvenir> getList(){
		return list;
	}
	
	public void setList(List<Souvenir> list) {
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
