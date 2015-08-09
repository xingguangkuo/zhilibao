package cn.itcast.crm.dao.impl;

import org.springframework.stereotype.Repository;
import cn.itcast.crm.dao.ISysUserDao;
import cn.itcast.crm.domain.SysUser;

@Repository(ISysUserDao.SERVICE_NAME)
public class SysUserDaoImpl extends CommonDaoImpl<SysUser> implements ISysUserDao {
}
