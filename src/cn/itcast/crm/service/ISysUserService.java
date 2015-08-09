package cn.itcast.crm.service;

import java.util.List;

import cn.itcast.bean.SysUserSearch;
import cn.itcast.crm.domain.SysUser;

public interface ISysUserService {
	
	public final static String  SERVICE_NAME="cn.itcast.crm.service.impl.SysUserServiceImpl";

	/**
	 * 通过用户名和密码查询用户信息
	 * @param name
	 * @param password
	 * @return
	 */
	SysUser findSysUserByNameAndPassword(String name, String password);

	void saveSysUser(SysUser sysUser);

	/**
	 * 通过条件查询用户信息
	 * @param sysUserSearch
	 * @return
	 */
	List<SysUser> findSysUsersByCondition(SysUserSearch sysUserSearch);

	/**
	 * 通过id批量删除用户信息
	 * @param ids
	 */
	void deleteSysUsersByIds(Integer[] ids);
	
	/**
	 * 通过id批量启用用户信息
	 * @param ids
	 */
	void enableSysUsersByIds(Integer[] ids);
	
	/**
	 * 通过id批量停用用户信息
	 * @param ids
	 */
	void disableSysUsersByIds(Integer[] ids);

	/**
	 * 通过id查询用户信息
	 * @param id
	 * @return
	 */
	SysUser findSysUsersById(Integer id);

	/**
	 * 更新用户信息
	 * @param newSysUser
	 */
	void updateSysUser(SysUser newSysUser);

	/**
	 * 查询所有的用户
	 * @return
	 */
	List<SysUser> findAllSysUser();
}
