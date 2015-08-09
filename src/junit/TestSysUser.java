package junit;

import org.junit.Test;

import cn.itcast.crm.container.ServiceProvinder;
import cn.itcast.crm.dao.ISysUserDao;
import cn.itcast.crm.domain.SysRole;
import cn.itcast.crm.domain.SysUser;
import cn.itcast.crm.domain.SysUserGroup;
import cn.itcast.crm.util.MD5keyBean;

public class TestSysUser {

	@Test
	public void saveSysUser(){
		ISysUserDao sysUserDao=(ISysUserDao)ServiceProvinder.getService(ISysUserDao.SERVICE_NAME);
		
		SysUser sysUser=new SysUser();
		sysUser.setName("admin");
		sysUser.setCnname("系统管理员");
		
		SysUserGroup  sysUserGroup=new SysUserGroup();
		sysUserGroup.setId(21);
		sysUser.setSysUserGroup(sysUserGroup);
		
	    SysRole sysRole=new SysRole();
	    sysRole.setId("402881e4324d516701324d5370740002");
	    sysUser.setSysRole(sysRole);
		
		//密码
		MD5keyBean m = new MD5keyBean();
		String md5 = m.getkeyBeanofStr("123456");
		sysUser.setPassword(md5);
		
		sysUserDao.save(sysUser);
		
	}
}
