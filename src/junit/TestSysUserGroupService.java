package junit;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.crm.container.ServiceProvinder;
import cn.itcast.crm.domain.SysUserGroup;
import cn.itcast.crm.service.ISysUserGroupService;

public class TestSysUserGroupService {

	@Test
	public void testSave() {
		ISysUserGroupService sysUserGroupService=(ISysUserGroupService)ServiceProvinder.getService(ISysUserGroupService.SERVICE_NAME);
		SysUserGroup sysUserGroup = new SysUserGroup();
		sysUserGroup.setName("销售部");
		sysUserGroup.setPrincipal("xxx");
		sysUserGroup.setIncumbent("ttt");
		sysUserGroupService.saveSysUserGroup(sysUserGroup);
	}
	
	
	@Test
	public void testSavex() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		ISysUserGroupService sysUserGroupService = (ISysUserGroupService) ctx.getBean(ISysUserGroupService.SERVICE_NAME);
		SysUserGroup sysUserGroup = new SysUserGroup();
		sysUserGroup.setName("销售部");
		sysUserGroup.setPrincipal("xxx");
		sysUserGroup.setIncumbent("ttt");		
		sysUserGroupService.saveSysUserGroup(sysUserGroup);
	}
	
	
//	/**
//	 * 该测试类模拟的是struts2的action层
//	 *   在该测试类中调用业务层
//	 */
//	@Test
//	public void findObjectsByConditionWithNoPage() {
//		ISysUserGroupService sysUserGroupService=(ISysUserGroupService)ServiceProvinder.getService(ISysUserGroupService.SERVICE_NAME);
//		
//		//获取部门名称
//		String name="销售部";
//		
//		//获取部门负责人
//		String principal="tom";
//		
//		@SuppressWarnings("unused")
//		List<SysUserGroup> list=sysUserGroupService.findSysUserGroups(name,principal);
//	}
//	
}
