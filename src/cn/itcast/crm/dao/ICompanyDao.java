package cn.itcast.crm.dao;

import cn.itcast.crm.domain.Company;

public interface ICompanyDao extends ICommonDao<Company> {
	public final static String  SERVICE_NAME="cn.itcast.crm.dao.impl.CompanyDaoImpl";
}
