package cn.itcast.crm.service;

import java.util.List;
import cn.itcast.crm.domain.SysDictionaryType;


public interface ISysDictionaryTypeService {
	public final static String  SERVICE_NAME="cn.itcast.crm.service.impl.SysDictionaryTypeServiceImpl";
	/**
	 * 通过code来查询sys_dictionary_type表的信息
	 * @param code
	 * @return
	 */
	List<SysDictionaryType> findSysDictionaryTypeByCode(String code);
}

