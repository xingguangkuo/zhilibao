package cn.itcast.crm.dao.impl;

import org.springframework.stereotype.Repository;
import cn.itcast.crm.dao.ISysMenuDao;
import cn.itcast.crm.domain.SysMenu;

@Repository(ISysMenuDao.SERVICE_NAME)
public class SysMenuDaoImpl extends CommonDaoImpl<SysMenu> implements ISysMenuDao {
	
}
