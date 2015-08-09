package cn.itcast.crm.dao;

import cn.itcast.crm.domain.City;

public interface ICityDao extends ICommonDao<City> {
	public final static String  SERVICE_NAME="cn.itcast.crm.dao.impl.CityDaoImpl";
}
