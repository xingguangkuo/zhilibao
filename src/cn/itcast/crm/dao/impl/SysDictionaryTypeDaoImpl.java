package cn.itcast.crm.dao.impl;

import org.springframework.stereotype.Repository;

import cn.itcast.crm.dao.ISysDictionaryTypeDao;
import cn.itcast.crm.domain.SysDictionaryType;

@Repository(ISysDictionaryTypeDao.SERVICE_NAME)
public class SysDictionaryTypeDaoImpl extends CommonDaoImpl<SysDictionaryType> implements ISysDictionaryTypeDao {

}
