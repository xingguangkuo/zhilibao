package cn.itcast.crm.dao.impl;

import org.springframework.stereotype.Repository;

import cn.itcast.crm.dao.ISysCodeRuleDao;
import cn.itcast.crm.domain.SysCodeRule;

@Repository(ISysCodeRuleDao.SERVICE_NAME)
public class SysCodeRuleDaoImpl extends  CommonDaoImpl<SysCodeRule> implements ISysCodeRuleDao {

}
