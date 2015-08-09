package cn.itcast.crm.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.crm.dao.ICityDao;
import cn.itcast.crm.domain.City;
import cn.itcast.crm.service.ICityService;

@Transactional(readOnly=true)
@Service(ICityService.SERVICE_NAME)
public class CityServiceImpl implements ICityService{

	@Resource(name=ICityDao.SERVICE_NAME)
	private ICityDao cityDao;
	
	public List<City> findCitiesByPid(Integer id) {
		if(id!=null){
		    String whereHql=" and o.pid=?";
		    Object[] params={id};
		    LinkedHashMap<String, String> orderby=new LinkedHashMap<String, String>();
		    orderby.put("o.id","asc");
		    return cityDao.findObjectsByConditionWithNoPage(whereHql, params, orderby);
		}
		return null;
	}

}
