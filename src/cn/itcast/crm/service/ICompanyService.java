package cn.itcast.crm.service;

import java.sql.Date;
import java.util.List;

import cn.itcast.bean.CompanySearch;
import cn.itcast.crm.domain.Company;
import cn.itcast.crm.domain.SysUser;

public interface ICompanyService {
	public final static String  SERVICE_NAME="cn.itcast.crm.service.impl.CompanyServiceImpl";

	/**
	 * 生成客户编码
	 * @param tabName
	 * @return
	 */
	String getCompanyCodeByTabName(String tabName);

	/**
	 * 保存客户的信息
	 * @param curSysUser;处理日志用的
	 * @param company
	 */
	void saveCompany(SysUser curSysUser, Company company);

	/**
	 * @param curSysuser 当前的登陆用户
	 * @param companySearch 查询条件
	 * @return
	 */
	List<Company> findCompanysCondition(SysUser curSysuser,
			CompanySearch companySearch);

	/**
	 * 通过客户id获取客户的信息
	 * @param id
	 * @return
	 */
	Company findCompanyById(Integer id);

	/**
	 * 修改客户信息
	 * @param curSysUser
	 * @param company
	 */
	void updateCompany(SysUser curSysUser, Company company);

	/**
	 * 批量删除客户信息
	 * @param ids
	 */
	void deleteCompanyByIds(Integer[] ids);

	/**
	 * 增加共享
	 * @param s_module:模块名称
	 * @param id:客户id
	 * @param uids;用户id的数组
	 */
	void addUpdateShareSetOne(String s_module, Integer id, Integer[] uids);
	
	/**
	 * 减少共享
	 * @param s_module:模块名称
	 * @param id:客户id
	 * @param uids;用户id的数组
	 */
	void minusUpdateShareSetOne(String s_module, Integer id, Integer[] uids);

	/**
	 * 	取消共享
	 * @param id  客户id
	 * @param s_module  模块名称
	 */
	void updateshareCancelOne(Integer id, String s_module);

	/**
	 * 获取客户对应的共享的用户信息
	 * @param id  客户id
	 * @return
	 */
	List<SysUser> findSysUsersBySharedIds(Integer id);

	/**
	 * 修改客户的下次联系时间
	 * @param id   客户的id  数组
	 * @param next_touch_date  下次联系时间
	 */
	void updateNextTouchTime(Integer[] id, Date next_touch_date);

	/**
	 * 变更客户的持有人
	 * @param id
	 * @param new_owner
	 */
	void changeHandler(Integer[] id, Integer new_owner);
}
