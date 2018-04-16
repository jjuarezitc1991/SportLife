package com.test.beans;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.test.beans.base.BaseBean;
import com.test.domain.Clothing;
import com.test.services.IClothingService;
import com.test.services.base.IService;
import com.test.utils.URLUtils;

@ManagedBean
public class ClothingBean extends BaseBean<Clothing>{
	
	private static final long serialVersionUID = 1L;	
	private List<Clothing> list;
	
	@ManagedProperty(value = "#{clothingService}")
	private IClothingService clothingService;
	
	public ClothingBean() {
		super(Clothing.class);
	}
	
	@PostConstruct
    public void init() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance()
        														.getExternalContext().getRequest();
        String uri = request.getRequestURI();
        int teamId = URLUtils.getTeamNameInURI(uri);
        list = clothingService.getClothingByTeamId(teamId);
    }
	
	@Override
	public String getListViewName() {
		return "clothing";
	}
	@Override
	protected IService<Clothing> getPersistenceService() {
		return clothingService;
	}
	
	public IClothingService getClothingService() {
		return clothingService;
	}

	public void setClothingService(IClothingService clothingService) {
		this.clothingService = clothingService;
	}
	
	public List<Clothing> getList(){
		return list;
	}
	
	public void setList(List<Clothing> list) {
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
