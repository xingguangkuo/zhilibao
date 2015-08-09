package cn.itcast.crm.service;

import java.util.List;

import cn.itcast.crm.domain.SysPopedom;

public interface ISysPopedomService {
	public final static String  SERVICE_NAME="cn.itcast.crm.service.impl.SysPopedomServiceImpl";

	/**
	 * 获取操作功能表的所有数据
	 * @return
	 */
	List<SysPopedom> findAllSysPopedoms();
}
