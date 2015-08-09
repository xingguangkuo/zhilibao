package cn.itcast.crm.dao.impl;

import org.springframework.stereotype.Repository;

import cn.itcast.crm.dao.IProvinceDao;
import cn.itcast.crm.domain.Province;

@Repository(IProvinceDao.SERVICE_NAME)
public class ProvinceDaoImpl extends CommonDaoImpl<Province> implements IProvinceDao {
}
