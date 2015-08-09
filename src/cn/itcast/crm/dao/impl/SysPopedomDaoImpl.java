package cn.itcast.crm.dao.impl;

import org.springframework.stereotype.Repository;
import cn.itcast.crm.dao.ISysPopedomDao;
import cn.itcast.crm.domain.SysPopedom;

@Repository(ISysPopedomDao.SERVICE_NAME)
public class SysPopedomDaoImpl extends CommonDaoImpl<SysPopedom> implements ISysPopedomDao {

}
