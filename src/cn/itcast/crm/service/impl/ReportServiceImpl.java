package cn.itcast.crm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bean.ReportBean;
import cn.itcast.crm.dao.IReportDao;
import cn.itcast.crm.service.IReportService;

@Transactional(readOnly=true)
@Service(IReportService.SERVICE_NAME)
public class ReportServiceImpl implements IReportService {

	@Resource(name=IReportDao.SERVICE_NAME)
	private IReportDao reportDao;
	
	public List<ReportBean> findReportBeans() {
		List<ReportBean> list=reportDao.findReportBeans();
		return list;
	}
	
}
