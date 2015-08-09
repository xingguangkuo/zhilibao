package cn.itcast.crm.dao.impl;

import org.springframework.stereotype.Repository;

import cn.itcast.crm.dao.ISysOperateLogDao;
import cn.itcast.crm.domain.SysOperateLog;

@Repository(ISysOperateLogDao.SERVICE_NAME)
public class SysOperateLogDaoImpl extends CommonDaoImpl<SysOperateLog> implements ISysOperateLogDao {

}
