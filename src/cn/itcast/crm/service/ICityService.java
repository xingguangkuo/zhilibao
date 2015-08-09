package cn.itcast.crm.service;

import java.util.List;

import cn.itcast.crm.domain.City;

public interface ICityService {
	public final static String  SERVICE_NAME="cn.itcast.crm.service.impl.CityServiceImpl";

	/**
	 * 通过省的id获取该省得城市信息s
	 * @param id
	 * @return
	 */
	List<City> findCitiesByPid(Integer id);
}
