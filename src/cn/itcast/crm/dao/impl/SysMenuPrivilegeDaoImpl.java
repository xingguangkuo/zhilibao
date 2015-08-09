package cn.itcast.crm.dao.impl;

import org.springframework.stereotype.Repository;
import cn.itcast.crm.dao.ISysMenuPrivilegeDao;
import cn.itcast.crm.domain.SysMenuPrivilege;

@Repository(ISysMenuPrivilegeDao.SERVICE_NAME)
public class SysMenuPrivilegeDaoImpl extends CommonDaoImpl<SysMenuPrivilege> implements ISysMenuPrivilegeDao {

}
