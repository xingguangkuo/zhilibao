package cn.itcast.crm.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.crm.dao.ISysMenuPrivilegeDao;
import cn.itcast.crm.domain.SysMenuPrivilege;
import cn.itcast.crm.domain.SysMenuPrivilegeId;
import cn.itcast.crm.service.ISysMenuPrivilegeService;

@Transactional(readOnly=true)
@Service(ISysMenuPrivilegeService.SERVICE_NAME)
public class SysMenuPrivilegeServiceImpl implements ISysMenuPrivilegeService {

	@Resource(name=ISysMenuPrivilegeDao.SERVICE_NAME)
	private ISysMenuPrivilegeDao sysMenuPrivilegeDao;
	
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void updateMenu(String roleId, String[] menuModules) {
		if(StringUtils.isNotBlank(roleId)&&menuModules!=null&&menuModules.length>0){
			//删除权限组对应的权限
			String whereHql=" and o.id.roleId=?";
			Object[] params={roleId};
			List<SysMenuPrivilege> list=sysMenuPrivilegeDao.findObjectsByConditionWithNoPage(whereHql, params);
			sysMenuPrivilegeDao.deleteAllObjects(list);
		
		    //增加权限到权限组中
			for(int i=0;i<menuModules.length;i++){
				if(StringUtils.isNotBlank(menuModules[i])){
					String[] str=menuModules[i].split(",");
					SysMenuPrivilege s=new SysMenuPrivilege();
					SysMenuPrivilegeId id=new SysMenuPrivilegeId();
					id.setRoleId(roleId);
					id.setMenuModule(str[0]);
					id.setMenuPrivilege(str[1]);
					s.setId(id);
					sysMenuPrivilegeDao.save(s);
				}
			}
		}
	}

	public List<SysMenuPrivilege> findsysMenuPrivilegesByRoleId(String roleId) {
		if(StringUtils.isNotBlank(roleId)){
			String whereHql=" and o.id.roleId=?";
			Object[] params={roleId};
			LinkedHashMap<String, String> orderby=new LinkedHashMap<String, String>();
			List<SysMenuPrivilege> SysMenuPrivileges=sysMenuPrivilegeDao.findObjectsByConditionWithNoPage(whereHql, params);
			return SysMenuPrivileges;
		}
		return null;
	}

	public List<SysMenuPrivilege> findAllSysMenuPrivileges() {
		LinkedHashMap<String, String> orderby=new LinkedHashMap<String, String>();
		orderby.put("o.id.roleId", "asc");
		return sysMenuPrivilegeDao.findObjectsByConditionWithNoPage(orderby);
	}

	public List<SysMenuPrivilege> findAllSysMenuPrivilegesCache() {
		LinkedHashMap<String, String> orderby=new LinkedHashMap<String, String>();
		orderby.put("o.id.roleId", "asc");
		return sysMenuPrivilegeDao.findObjectsByConditionWithNoPageCache(null,null,orderby);
	}
}
