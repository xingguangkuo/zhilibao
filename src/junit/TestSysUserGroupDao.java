package junit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.crm.container.ServiceProvinder;
import cn.itcast.crm.dao.ISysUserGroupDao;
import cn.itcast.crm.domain.SysUserGroup;

public class TestSysUserGroupDao {

	@Test
	public void testSave() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		ISysUserGroupDao sysUserGroupDao = (ISysUserGroupDao) ctx.getBean(ISysUserGroupDao.SERVICE_NAME);
		SysUserGroup sysUserGroup = new SysUserGroup();
		sysUserGroup.setName("销售部");
		sysUserGroup.setPrincipal("xxx");
		sysUserGroup.setIncumbent("ttt");
		sysUserGroupDao.save(sysUserGroup);
	}
	
	@Test
	public void testUpdate() {
		ISysUserGroupDao sysUserGroupDao =(ISysUserGroupDao)ServiceProvinder.getService(ISysUserGroupDao.SERVICE_NAME);
		SysUserGroup sysUserGroup = new SysUserGroup();
		sysUserGroup.setId(1);
		sysUserGroup.setName("销售部01");
		sysUserGroup.setPrincipal("tom");
		sysUserGroup.setIncumbent("销售部");
		sysUserGroupDao.update(sysUserGroup);
	}
	
	@Test
	public void findSysUserGroupById() {
		ISysUserGroupDao sysUserGroupDao =(ISysUserGroupDao)ServiceProvinder.getService(ISysUserGroupDao.SERVICE_NAME);
	    Serializable id=1; 
	    SysUserGroup sysUserGroup=sysUserGroupDao.findObjectById(id);
	}
	
	@Test
	public void deleteByIds() {
		ISysUserGroupDao sysUserGroupDao =(ISysUserGroupDao)ServiceProvinder.getService(ISysUserGroupDao.SERVICE_NAME);
	    Serializable[] ids={7,8,9}; 
	    sysUserGroupDao.deleteByIds(ids);
	}
	
	@Test
	public void deleteCollections() {
		ISysUserGroupDao sysUserGroupDao =(ISysUserGroupDao)ServiceProvinder.getService(ISysUserGroupDao.SERVICE_NAME);
		SysUserGroup s1 = new SysUserGroup();
		s1.setId(5);
		s1.setName("销售部01");
		
		SysUserGroup s2 = new SysUserGroup();
		s2.setId(6);
		s2.setName("销售部01");
		
		List<SysUserGroup> list=new ArrayList<SysUserGroup>();
		list.add(s1);
		list.add(s2);
		
	    sysUserGroupDao.deleteAllObjects(list);
	}
	
}
