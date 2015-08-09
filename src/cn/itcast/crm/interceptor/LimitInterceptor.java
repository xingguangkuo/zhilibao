package cn.itcast.crm.interceptor;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import cn.itcast.crm.annotation.Limit;
import cn.itcast.crm.container.ServiceProvinder;
import cn.itcast.crm.domain.SysPopedomPrivilege;
import cn.itcast.crm.domain.SysUser;
import cn.itcast.crm.service.ISysPopedomPrivilegeService;
import cn.itcast.crm.util.SessionUtils;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

@SuppressWarnings("serial")
public class LimitInterceptor  extends MethodFilterInterceptor{

	public String doIntercept(ActionInvocation invocation) throws Exception {
		//获取请求的action对象
		Object action=invocation.getAction();
		
		//获取请求的方法的名称
		String methodName=invocation.getProxy().getMethod();
		System.out.println("methodName  "+methodName);
		
		//获取action中的方法的封装类(action中的方法没有参数)
		Method method=action.getClass().getMethod(methodName, null);

		//获取request对象
		HttpServletRequest request=ServletActionContext.getRequest();
		
		//检查注解
		boolean flag=isCheckLimit(request,method);
		
		
		if(!flag){
			System.out.println("没有权限");
			//没有权限
			return "popmsg_popedom";
		}
		System.out.println("有权限");
		
		//有权限,可以调用action中的方法
		String returnvalue=invocation.invoke();
		return returnvalue;
	}

	public boolean isCheckLimit(HttpServletRequest request, Method method) {
		if(method==null){
			return false;
		}
		
		//获取当前的登陆用户
		SysUser sysUser=SessionUtils.getSysUserFormSession(request);
		if(sysUser==null){
			return false;
		}
		
		if(sysUser.getSysRole()==null){
			return false;
		}
		
		//获取当前登陆用户的权限组id
		String roleId=sysUser.getSysRole().getId();
		//处理注解
		/*
		 * 	@Limit(module="group",privilege="list")
	        public String list(){
		 */
		boolean isAnnotationPresent= method.isAnnotationPresent(Limit.class);
		
		//不存在注解
		if(!isAnnotationPresent){
			return false;
		}
		
		//存在注解
		Limit limit=method.getAnnotation(Limit.class);
		
		//获取注解上的值
		String module=limit.module();  //模块名称
		String privilege=limit.privilege(); //操作名称
		
		/**
		 * 如果登陆用户的权限组id+注解上的@Limit(module="group",privilege="list")
		 *   * 在sys_popedom_privilege表中存在   flag=true;
		 *   * 在sys_popedom_privilege表中不存在 flag=false;
		 */
		boolean flag=false;
		
		//查询sys_popedom_privilege表中的所有的数据
		ISysPopedomPrivilegeService sysPopedomPrivilegeService=
		    (ISysPopedomPrivilegeService)ServiceProvinder.getService(ISysPopedomPrivilegeService.SERVICE_NAME);
	
		List<SysPopedomPrivilege> list=sysPopedomPrivilegeService.findAllSysPopedomPrivilegesCache();
		if(list!=null&&list.size()>0){
		  for(int i=0;i<list.size();i++){
			  SysPopedomPrivilege s=list.get(i);
			  if(s!=null){
				   if(roleId.equals(s.getId().getRoleId())&&module.equals(s.getId().getPopedomModule())
						   &&privilege.equals(s.getId().getPopedomPrivilege())){
					   flag=true;
					   break;
				   }
			  }
		  }
		}

		return flag;
	}
}
