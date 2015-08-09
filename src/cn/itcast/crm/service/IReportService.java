package cn.itcast.crm.service;

import java.util.List;

import cn.itcast.bean.ReportBean;

public interface IReportService {
	public final static String  SERVICE_NAME="cn.itcast.crm.service.impl.ReportServiceImpl";

	List<ReportBean> findReportBeans();
}
