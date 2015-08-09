package cn.itcast.crm.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.crm.dao.ISysPopedomPrivilegeDao;
import cn.itcast.crm.domain.SysPopedomPrivilege;
import cn.itcast.crm.domain.SysPopedomPrivilegeId;
import cn.itcast.crm.service.ISysPopedomPrivilegeService;

@Transactional(readOnly=true)
@Service(ISysPopedomPrivilegeService.SERVICE_NAME)
public class SysPopedomPrivilegeServiceImpl implements ISysPopedomPrivilegeService {

	@Resource(name=ISysPopedomPrivilegeDao.SERVICE_NAME)
	private ISysPopedomPrivilegeDao sysPopedomPrivilegeDao;
	
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void updatePopedom(String roleId, String[] popedomModules) {
		
		//删除该权限组对应的所有权限
		if(StringUtils.isNotBlank(roleId)&&popedomModules!=null&&popedomModules.length>0){
			String whereHql="and o.id.roleId=?";
		    Object[] params={roleId};
			List<SysPopedomPrivilege> list=sysPopedomPrivilegeDao.findObjectsByConditionWithNoPage(whereHql, params);
			sysPopedomPrivilegeDao.deleteAllObjects(list);
		}
		
		//保存该权限组对应的所有权限
		if(StringUtils.isNotBlank(roleId)&&popedomModules!=null&&popedomModules.length>0){
			for(int i=0;i<popedomModules.length;i++){
				if(StringUtils.isNotBlank(popedomModules[i])){
			         String[] str=popedomModules[i].split(",");
			         SysPopedomPrivilege sysPopedomPrivilege=new SysPopedomPrivilege();
			         
			         SysPopedomPrivilegeId id=new SysPopedomPrivilegeId();
			         id.setRoleId(roleId);
			         id.setPopedomModule(str[0]);
			         id.setPopedomPrivilege(str[1]);
			         sysPopedomPrivilege.setId(id);
			         sysPopedomPrivilegeDao.save(sysPopedomPrivilege);
			         
				}
			}	
		}
	}

	public List<SysPopedomPrivilege> findSysPopedomPrivilegesByRoleId(String roleId) {
		if(StringUtils.isNotBlank(roleId)){
			String whereHql="and o.id.roleId=?";
		    Object[] params={roleId};
			List<SysPopedomPrivilege> list=sysPopedomPrivilegeDao.findObjectsByConditionWithNoPage(whereHql, params);
		    return list;
		}
		
		return null;
	}

	public List<SysPopedomPrivilege> findAllSysPopedomPrivileges() {
		LinkedHashMap<String, String> orderby=new LinkedHashMap<String, String>();
		orderby.put("o.id.roleId", "asc");
		return sysPopedomPrivilegeDao.findObjectsByConditionWithNoPage(orderby);
	}

	@Override
	public List<SysPopedomPrivilege> findAllSysPopedomPrivilegesCache() {
		LinkedHashMap<String, String> orderby=new LinkedHashMap<String, String>();
		orderby.put("o.id.roleId", "asc");
		return sysPopedomPrivilegeDao.findObjectsByConditionWithNoPageCache(null,null,orderby);
	}
}
