package cn.itcast.crm.dao.impl;

import org.springframework.stereotype.Repository;
import cn.itcast.crm.dao.ICompanyDao;
import cn.itcast.crm.domain.Company;

@Repository(ICompanyDao.SERVICE_NAME)
public class CompanyDaoImpl extends CommonDaoImpl<Company> implements ICompanyDao {


}
