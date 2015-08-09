package cn.itcast.crm.dao.impl;

import org.springframework.stereotype.Repository;
import cn.itcast.crm.dao.ISysRoleDao;
import cn.itcast.crm.domain.SysRole;

@Repository(ISysRoleDao.SERVICE_NAME)
public class SysRoleDaoImpl extends CommonDaoImpl<SysRole> implements ISysRoleDao {

}
