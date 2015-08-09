package cn.itcast.crm.service;

import java.util.List;
import cn.itcast.crm.domain.SysMenu;

public interface ISysMenuService {
	public final static String  SERVICE_NAME="cn.itcast.crm.service.impl.SysMenuServiceImpl";
	List<SysMenu> findAllSysMenus();
	List<SysMenu> findAllSysMenusCache();
}
