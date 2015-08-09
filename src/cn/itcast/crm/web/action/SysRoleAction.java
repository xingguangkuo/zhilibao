package cn.itcast.crm.web.action;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import cn.itcast.bean.SysRoleSearch;
import cn.itcast.crm.annotation.Limit;
import cn.itcast.crm.container.ServiceProvinder;
import cn.itcast.crm.domain.SysMenu;
import cn.itcast.crm.domain.SysMenuPrivilege;
import cn.itcast.crm.domain.SysPopedom;
import cn.itcast.crm.domain.SysPopedomPrivilege;
import cn.itcast.crm.domain.SysRole;
import cn.itcast.crm.service.ISysMenuPrivilegeService;
import cn.itcast.crm.service.ISysMenuService;
import cn.itcast.crm.service.ISysPopedomPrivilegeService;
import cn.itcast.crm.service.ISysPopedomService;
import cn.itcast.crm.service.ISysRoleService;
import cn.itcast.crm.web.form.SysRoleForm;

import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class SysRoleAction extends BaseAction implements ModelDriven<SysRoleForm> {

	private SysRoleForm sysRoleForm=new SysRoleForm();
	
	//获取权限组的业务层对象
	private ISysRoleService sysRoleService=
		                    (ISysRoleService)ServiceProvinder.getService(ISysRoleService.SERVICE_NAME);

	//获取操作功能的业务层1
	private ISysPopedomService sysPopedomService=
		                       (ISysPopedomService)ServiceProvinder.getService(ISysPopedomService.SERVICE_NAME);
	
	//获取操作权限表的业务层2
	private ISysPopedomPrivilegeService sysPopedomPrivilegeService=
		    (ISysPopedomPrivilegeService)ServiceProvinder.getService(ISysPopedomPrivilegeService.SERVICE_NAME);
	
	//获取菜单功能的业务层
	private ISysMenuService sysMenuService=
		    (ISysMenuService)ServiceProvinder.getService(ISysMenuService.SERVICE_NAME);

	//获取菜单权限的业务层
	private ISysMenuPrivilegeService sysMenuPrivilegeService=
		    (ISysMenuPrivilegeService)ServiceProvinder.getService(ISysMenuPrivilegeService.SERVICE_NAME);
	
	/**
	 * 设置权限组权限
	 * @return
	 */
	@Limit(module="role",privilege="updateMenu")
	public String updateMenu(){
		String roleId=request.getParameter("roleId");
        if(StringUtils.isNotBlank(roleId)){		
			//通过权限组id查询权限组信息
			SysRole sysRole=sysRoleService.findSysRoleById(roleId);
			//放置到request中
			request.setAttribute("sysRole",sysRole);

			//获取选中的复选框的值
			String[] menuModules=request.getParameterValues("menuModule");
			
            //调用设置菜单权限的业务层	
			sysMenuPrivilegeService.updateMenu(roleId,menuModules);
			
			
			return "updateMenu";
		}
        return null;
    }

	/**
	 * 显示菜单
	 * @return
	 */
	@Limit(module="role",privilege="listMenu")
	public String listMenu(){
		//获取权限组id
		String roleId=request.getParameter("roleId");
        if(StringUtils.isNotBlank(roleId)){		
			//通过权限组id查询权限组信息
			SysRole sysRole=sysRoleService.findSysRoleById(roleId);
			//放置到request中
			request.setAttribute("sysRole",sysRole);
			
			//查询sys_menu表中的所有数据
			List<SysMenu>  sysMenus=sysMenuService.findAllSysMenus();
			request.setAttribute("sysMenus", sysMenus);
			
			
			//通过权限组id查询sys_menu_privilege
			List<SysMenuPrivilege> sysMenuPrivileges=sysMenuPrivilegeService.findsysMenuPrivilegesByRoleId(roleId);
			request.setAttribute("sysMenuPrivileges", sysMenuPrivileges);
			
			return "listMenu";
        }
	   return null;
	}
	
	
	@Limit(module="role",privilege="updatePopedom")
	public String updatePopedom(){
		//获取角色id
		String roleId=request.getParameter("roleId");
		SysRole sysRole=sysRoleService.findSysRoleById(roleId);
		request.setAttribute("sysRole",sysRole);
		
		//获取复选框的值
		String[] popedomModules=request.getParameterValues("popedomModule");
		sysPopedomPrivilegeService.updatePopedom(roleId,popedomModules);
		
		return "updatePopedom";
	}
	
	/**
	 * 显示系统的所有操作功能
	 * @return
	 */
	@Limit(module="role",privilege="listPopedom")
	public String listPopedom(){
		//获取角色id
		String roleId=request.getParameter("roleId");
		SysRole sysRole=sysRoleService.findSysRoleById(roleId);
		request.setAttribute("sysRole",sysRole);
		
		//获取系统的所有功能(数据来源于操作功能表)
		List<SysPopedom>  sysPopedoms=sysPopedomService.findAllSysPopedoms();
		request.setAttribute("sysPopedoms", sysPopedoms);
		
		//查询该权限组包含的权限
		List<SysPopedomPrivilege> sysPopedomPriviles=sysPopedomPrivilegeService.findSysPopedomPrivilegesByRoleId(roleId);
		request.setAttribute("sysPopedomPriviles", sysPopedomPriviles);
		
		//<result name="listPopedom">/WEB-INF/page/sys/role/popedom/view.jsp</result>
		return "listPopedom";
	}
	
	
	/**
	 * 保存权限组信息
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Limit(module="role",privilege="save")
	public String save() throws IllegalAccessException, InvocationTargetException {
		//实例化po对象
		SysRole sysRole=new SysRole();
		
		//赋值vo--->po
		BeanUtils.copyProperties(sysRole, sysRoleForm);
		
		//调用权限组的业务层
		sysRoleService.saveSysRole(sysRole);
		
		return "listAction";
	}

	/**
	 * 修改权限组信息
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Limit(module="role",privilege="update")
	public String update() throws IllegalAccessException, InvocationTargetException {
		//实例化po对象
		SysRole sysRole=new SysRole();
		
		//赋值vo--->po
		BeanUtils.copyProperties(sysRole, sysRoleForm);
		
		//调用权限组的业务层
		sysRoleService.updateSysRole(sysRole);
		
		return "listAction";
	}
	
    /**
     * 删除权限组信息
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
	@Limit(module="role",privilege="delete")
	public String delete() throws IllegalAccessException, InvocationTargetException {
		
		//获取删除的权限组id
		String[] ids=request.getParameterValues("ids");
		if(ids!=null&&ids.length>0){
		  
		  //调用权限组的业务层删除
		  sysRoleService.deleteSysRolesByIds(ids);
		  return "listAction";
		}
		
		return null;
		
	}
	
	/**
	 * 显示权限组信息修改页面
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@Limit(module="role",privilege="edit")
	public String edit() throws IllegalAccessException, InvocationTargetException {
		
 		//获取权限组id
        String id=request.getParameter("id");		
		
		//根据id查询权限组的信息
		SysRole sysRole=sysRoleService.findSysRoleById(id);
		
		//放置权限组对象中的值到模型驱动对象中(po----vo)
		BeanUtils.copyProperties(sysRoleForm, sysRole);
		
		return "edit";
	}
		
	/**
	 * 显示权限组添加页面
	 * @return
	 */
	@Limit(module="role",privilege="add")
	public String add() {
		return "add";
	}

	/**
	 * 查询操作权限组的信息
	 * @return
	 */
	@Limit(module="role",privilege="list")
	public String list() {
		
		//实例化SysRoleSearch
		SysRoleSearch  sysRoleSearch=new SysRoleSearch();
		sysRoleSearch.setName(sysRoleForm.getName());
		

	    //调用业务层方法查询
		List<SysRole> sysRoles=sysRoleService.findSysRoles(sysRoleSearch);
		request.setAttribute("sysRoles", sysRoles);
		return "list";
	}

	public SysRoleForm getModel() {
		return sysRoleForm;
	}

}
