package cn.itcast.crm.dao.impl;

import org.springframework.stereotype.Repository;
import cn.itcast.crm.dao.ICityDao;
import cn.itcast.crm.domain.City;

@Repository(ICityDao.SERVICE_NAME)
public class CityDaoImpl extends CommonDaoImpl<City> implements ICityDao {
	
}
