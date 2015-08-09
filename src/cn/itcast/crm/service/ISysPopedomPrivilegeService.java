package cn.itcast.crm.service;

import java.util.List;

import cn.itcast.crm.domain.SysPopedomPrivilege;

public interface ISysPopedomPrivilegeService {
	public final static String  SERVICE_NAME="cn.itcast.crm.service.impl.SysPopedomPrivilegeServiceImpl";

	/**
	 * 设置权限组的权限
	 * @param roleId
	 * @param popedomModules
	 */
	void updatePopedom(String roleId, String[] popedomModules);

	/**
	 * 通过权限组的id查询该权限包含的权限  sys_popedom_privilege表中的数据
	 * @param roleId
	 * @return
	 */
	List<SysPopedomPrivilege> findSysPopedomPrivilegesByRoleId(String roleId);

	/**
	 * 查询所有的sys_popedom_privilege表中的数据
	 * @return
	 */
	List<SysPopedomPrivilege> findAllSysPopedomPrivileges();

	/**
	 * 查询所有的sys_popedom_privilege表中的数据,启动二级缓存
	 * @return
	 */
	List<SysPopedomPrivilege> findAllSysPopedomPrivilegesCache();
}
