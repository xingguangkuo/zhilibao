package cn.itcast.crm.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTag;

import org.apache.commons.lang.StringUtils;

import cn.itcast.crm.container.ServiceProvinder;
import cn.itcast.crm.domain.SysMenuPrivilege;
import cn.itcast.crm.domain.SysUser;
import cn.itcast.crm.service.ISysMenuPrivilegeService;
import cn.itcast.crm.util.SessionUtils;

public class CheckMemuTag implements SimpleTag {

	private PageContext pageContext;
	//标签体
	private JspFragment jspFragment;
	
	//<itcast:checkMemu  module="${sysMenu.id.menuModule}"   privilege="${sysMenu.id.menuPrivilege}">
	private String module;
	private String privilege;

	public void doTag() throws JspException, IOException {
   
		//获取request对象
		HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
		
		//获取当前登陆用户
		SysUser sysUser=SessionUtils.getSysUserFormSession(request);
		if(sysUser==null){
			return;
		}
		
		if(sysUser.getSysRole()==null){
			return;
		}
		
		//获取当前登陆用户对应的权限组id
		String roleId=sysUser.getSysRole().getId();
		
		//获取菜单操作权限的业务层  sys_menu_privilege
		ISysMenuPrivilegeService sysMenuPrivilegeService=
		    (ISysMenuPrivilegeService)ServiceProvinder.getService(ISysMenuPrivilegeService.SERVICE_NAME);
		
		//获取菜单操作权限表中的所有数据 sys_menu_privilege，返回值List集合
		List<SysMenuPrivilege> list=sysMenuPrivilegeService.findAllSysMenuPrivilegesCache();
		
		//遍历集合
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				SysMenuPrivilege s=list.get(i);
				if(StringUtils.isNotBlank(roleId)&&StringUtils.isNotBlank(module)&&StringUtils.isNotBlank(privilege)){
				    //比对权限id+模块名称+操作名称
		             if(roleId.equals(s.getId().getRoleId())&&module.equals(s.getId().getMenuModule())
		                &&privilege.equals(s.getId().getMenuPrivilege())){
                          //如果在集合中存在,输出标签体
		                  this.jspFragment.invoke(null);
		                  break;
		               }
				}
			}
		}
		     
		
	}

	public JspTag getParent() {
		return null;
	}

	public void setJspBody(JspFragment jspFragment) {
        this.jspFragment=jspFragment;
	}

	public void setJspContext(JspContext pc) {
        this.pageContext=(PageContext) pc;
	}
	
	public void setParent(JspTag arg0) {
	}
	
	public void setModule(String module) {
		this.module = module;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	
}
