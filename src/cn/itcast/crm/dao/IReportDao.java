package cn.itcast.crm.dao;

import java.util.List;

import cn.itcast.bean.ReportBean;

public interface IReportDao extends ICommonDao<ReportBean> {
	public final static String  SERVICE_NAME="cn.itcast.crm.dao.impl.ReportDaoImpl";

	/**
	 * 查询报表的数据
	 * @return
	 */
	List<ReportBean> findReportBeans(); 
}
