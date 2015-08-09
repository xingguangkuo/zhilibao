package cn.itcast.crm.dao;

import cn.itcast.crm.domain.SysMenu;

public interface ISysMenuDao extends ICommonDao<SysMenu> {
	public final static String  SERVICE_NAME="cn.itcast.crm.dao.impl.SysMenuDaoImpl";
}
