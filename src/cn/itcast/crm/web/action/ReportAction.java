package cn.itcast.crm.web.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

import cn.itcast.bean.ReportBean;
import cn.itcast.crm.container.ServiceProvinder;
import cn.itcast.crm.service.IReportService;
import cn.itcast.crm.util.JFreeChartUtils;

@SuppressWarnings("serial")
public class ReportAction extends BaseAction {
	private IReportService reportService=
		  (IReportService)ServiceProvinder.getService(IReportService.SERVICE_NAME);
	
	public String khflfx() throws IOException{
		List<ReportBean> reportBeans=reportService.findReportBeans();
		request.setAttribute("reportBeans", reportBeans);
		
		//计算总数
		Long sum=0L;
		if(reportBeans!=null&&reportBeans.size()>0){
			for(ReportBean reportBean:reportBeans){
				sum=sum+reportBean.getCount();
			}
		}
		request.setAttribute("sum", sum);
		
		//获取servletContext对象
		ServletContext sc=ServletActionContext.getServletContext();
		
		//生成图片
		JFreeChartUtils.generalBarJpeg(reportBeans,request,sc);
		
		return "khflfx";
	}
	
}
