package cn.itcast.crm.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bean.SysRoleSearch;
import cn.itcast.crm.dao.ISysRoleDao;
import cn.itcast.crm.domain.SysRole;
import cn.itcast.crm.service.ISysRoleService;

@Transactional(readOnly=true)
@Service(ISysRoleService.SERVICE_NAME)
public class SysRoleServiceImpl implements ISysRoleService {

	@Resource(name=ISysRoleDao.SERVICE_NAME)
	private ISysRoleDao  sysRoleDao;
	
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveSysRole(SysRole sysRole) {
		sysRoleDao.save(sysRole);
	}

	@SuppressWarnings("unchecked")
	public List<SysRole> findSysRoles(SysRoleSearch sysRoleSearch) {
		if(sysRoleSearch==null){
			throw new RuntimeException("传递给业务层权限组查询条件的对象为空");
		}
		
		//组织查询条件,封装查询条件需要的数据
		@SuppressWarnings("unused")
		String whereHql="";
		List paramsList=new ArrayList();
		
		if(StringUtils.isNotBlank(sysRoleSearch.getName())){
			whereHql=" and o.name like ?";
			paramsList.add("%"+sysRoleSearch.getName().trim()+"%");
		}
		
		Object[] params=paramsList.toArray();
		
		//排序
		LinkedHashMap<String, String> orderby=new LinkedHashMap<String, String>();
		orderby.put("o.id","asc");
		
		
		return sysRoleDao.findObjectsByConditionWithNoPage(whereHql, params, orderby);
	}

	public SysRole findSysRoleById(String id) {
		return sysRoleDao.findObjectById(id);
	}

	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void updateSysRole(SysRole sysRole) {
		sysRoleDao.update(sysRole);
	}
	
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void deleteSysRolesByIds(String ... ids) {
		sysRoleDao.deleteByIds((java.io.Serializable[])ids);
	}

	public List<SysRole> findAllSysRoles() {
		LinkedHashMap<String, String> orderby=new LinkedHashMap<String, String>();
		orderby.put("o.id", "asc");
		return sysRoleDao.findObjectsByConditionWithNoPage(orderby);
	}

}
