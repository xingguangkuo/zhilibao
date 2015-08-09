package cn.itcast.crm.web.action;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import cn.itcast.bean.SysUserSearch;
import cn.itcast.crm.annotation.Limit;
import cn.itcast.crm.container.ServiceProvinder;
import cn.itcast.crm.domain.SysRole;
import cn.itcast.crm.domain.SysUser;
import cn.itcast.crm.domain.SysUserGroup;
import cn.itcast.crm.service.ISysRoleService;
import cn.itcast.crm.service.ISysUserGroupService;
import cn.itcast.crm.service.ISysUserService;
import cn.itcast.crm.util.DataType;
import cn.itcast.crm.util.MD5keyBean;
import cn.itcast.crm.util.SQLDateConverter;
import cn.itcast.crm.util.SessionUtils;
import cn.itcast.crm.web.form.SysUserForm;

import com.opensymphony.xwork2.ModelDriven;

public class SysUserAction extends BaseAction implements ModelDriven<SysUserForm> {
	private static final long serialVersionUID = 1L;
	private SysUserForm sysUserForm=new SysUserForm();
	
	private ISysUserService sysUserService=
		(ISysUserService)ServiceProvinder.getService(ISysUserService.SERVICE_NAME);
	//获取权限组的业务层对象
	private ISysRoleService sysRoleService=
		(ISysRoleService)ServiceProvinder.getService(ISysRoleService.SERVICE_NAME);
	//获取业务层的对象(本项目struts2和spring是分离的)
	private ISysUserGroupService sysUserGroupService=
		  (ISysUserGroupService)ServiceProvinder.getService(ISysUserGroupService.SERVICE_NAME);
	

	
	@Limit(module="user",privilege="edit")
	public String edit() throws IllegalAccessException, InvocationTargetException{
		//处理权限组的下拉选
		List<SysRole> sysRoleSelect=sysRoleService.findAllSysRoles();
		request.setAttribute("sysRoleSelect",sysRoleSelect);
		
		//处理所属部门的下拉选
		List<SysUserGroup> sysUserGroupSelect=sysUserGroupService.findAllSysUserGroups();
		request.setAttribute("sysUserGroupSelect",sysUserGroupSelect);
		
		//获取id
		String sid=request.getParameter("id");

		//转化为整形
		if(StringUtils.isNotBlank(sid)){
		    Integer id=Integer.parseInt(sid.trim());
			
			//调用业务层的方法,通过id查询用户信息
		    SysUser oldSysUser=sysUserService.findSysUsersById(id);
		    
			//po---v0
		    BeanUtils.copyProperties(sysUserForm, oldSysUser);
		    
		    //处理权限组下拉选得回显
		    if(oldSysUser.getSysRole()!=null){
		    	System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		       sysUserForm.setRoleId(oldSysUser.getSysRole().getId());
		    }
		    
		    //处理部门
		    if(oldSysUser.getSysUserGroup()!=null){
		    	System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		    	sysUserForm.setGroupId(oldSysUser.getSysRole().getId());
		    }
		    return "edit";
		}
		return null;
	}
	
	/**
	 * 停用
	 * @return
	 */
	@Limit(module="user",privilege="disable")
	public String disable(){
		//获取删除的id
		String[] sids=request.getParameterValues("ids");
		//转化整形
		Integer ids[]=DataType.converterStringArray2IntegerArray(sids);
		if(ids!=null&&ids.length>0){
			//启用
			sysUserService.disableSysUsersByIds(ids);
			return "listAction";
		}
		return null;
	}
	/**
	 * 启用
	 * @return
	 */
	@Limit(module="user",privilege="enable")
	public String enable(){
		//获取删除的id
		String[] sids=request.getParameterValues("ids");
		//转化整形
		Integer ids[]=DataType.converterStringArray2IntegerArray(sids);
		if(ids!=null&&ids.length>0){
			//启用
			sysUserService.enableSysUsersByIds(ids);
			return "listAction";
		}
		return null;
	}
	
	/**
	 * 删除
	 * @return
	 */
	@Limit(module="user",privilege="delete")
	public String delete(){
		//获取删除的id
		String[] sids=request.getParameterValues("ids");
		
		//转化整形
		Integer ids[]=DataType.converterStringArray2IntegerArray(sids);
		if(ids!=null&&ids.length>0){
			
			//删除
			sysUserService.deleteSysUsersByIds(ids);
			
			return "listAction";
		}
		
		return null;
	}
	
	/**
	 * 显示用户的查询页面
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Limit(module="user",privilege="list")
	public String list() throws IllegalAccessException, InvocationTargetException{
		//处理所属部门的下拉选
		List<SysUserGroup> sysUserGroupSelect=sysUserGroupService.findAllSysUserGroups();
		request.setAttribute("sysUserGroupSelect",sysUserGroupSelect);
		
		//封装查询条件
		SysUserSearch sysUserSearch=new SysUserSearch();
        BeanUtils.copyProperties(sysUserSearch, sysUserForm);
        
        List<SysUser> sysUserList=sysUserService.findSysUsersByCondition(sysUserSearch);
        
		request.setAttribute("sysUserList", sysUserList);
		return "list";
	}
	/**
	 * 显示用户信息的添加页面
	 * @return
	 */
	@Limit(module="user",privilege="add")
	public String add(){
		//处理权限组的下拉选
		List<SysRole> sysRoleSelect=sysRoleService.findAllSysRoles();
		request.setAttribute("sysRoleSelect",sysRoleSelect);
		
		//处理所属部门的下拉选
		List<SysUserGroup> sysUserGroupSelect=sysUserGroupService.findAllSysUserGroups();
		request.setAttribute("sysUserGroupSelect",sysUserGroupSelect);
		
		//处理创建人  修改人  创建时间  修改时间
        
		//获取当前的登录用户
		SysUser sysUser=SessionUtils.getSysUserFormSession(request);
		if(sysUser==null){
			return "login";
		}
		
		//设置创建人和修改人(添加信息,创建人和修改人一致)
		sysUserForm.setCreator(sysUser.getCnname());
		sysUserForm.setUpdater(sysUser.getCnname());
		
		String curDate=DateFormatUtils.format(new java.util.Date(), "yyyy-MM-dd HH:mm:ss");
		sysUserForm.setCreateTime(curDate);
		sysUserForm.setUpdateTime(curDate);
		return "add";
	}
	
	@Limit(module="user",privilege="save")
	public String save() throws IllegalAccessException, InvocationTargetException{
		//实例化po对象
         SysUser sysUser=new SysUser();		
         
         //注册转化器
         ConvertUtils.register(new SQLDateConverter(), java.sql.Date.class);
         
		//v0--->po
		BeanUtils.copyProperties(sysUser, sysUserForm);
		
		//处理特殊情况
		  
		  //处理密码
		  MD5keyBean m = new MD5keyBean();
		  String password = m.getkeyBeanofStr(sysUserForm.getPassword());
		  sysUser.setPassword(password);
		  
		   //权限组
		   SysRole sysRole=new SysRole();
		   sysRole.setId(sysUserForm.getRoleId());
		   sysUser.setSysRole(sysRole);
		   
		   //部门
		   SysUserGroup sysUserGroup=new SysUserGroup();
		   if(StringUtils.isNotBlank(sysUserForm.getGroupId())){
		      sysUserGroup.setId(Integer.parseInt(sysUserForm.getGroupId()));
		   }
		   sysUser.setSysUserGroup(sysUserGroup);
		
		//调用业务层的方法保存
		   sysUserService.saveSysUser(sysUser);
		
		return "listAction";
	}

	@Limit(module="user",privilege="update")
	public String update() throws IllegalAccessException, InvocationTargetException{
		//实例化po对象
         SysUser newSysUser=new SysUser();		
         
         //注册转化器
         ConvertUtils.register(new SQLDateConverter(), java.sql.Date.class);
         
		//v0--->po
		BeanUtils.copyProperties(newSysUser, sysUserForm);
		
		//处理特殊情况
		   //权限组
		   SysRole sysRole=new SysRole();
		   sysRole.setId(sysUserForm.getRoleId());
		   newSysUser.setSysRole(sysRole);
		   
		   //部门
		   SysUserGroup sysUserGroup=new SysUserGroup();
		   if(StringUtils.isNotBlank(sysUserForm.getGroupId())){
		      sysUserGroup.setId(Integer.parseInt(sysUserForm.getGroupId()));
		   }
		   
		   newSysUser.setSysUserGroup(sysUserGroup);
		
		   
		   //获取当前登陆的用户
		   SysUser curSysUser=SessionUtils.getSysUserFormSession(request);
		   if(curSysUser==null){
			   return "login";
		   }
		   
		   //设置修改者
		   newSysUser.setUpdater(curSysUser.getCnname());
		   //设置修改时间
		   newSysUser.setUpdateTime(DateFormatUtils.format(new java.util.Date(),"yyyy-MM-dd HH:mm:ss"));
		   
		   
		   //调用业务层的方法保存
		   sysUserService.updateSysUser(newSysUser);
		
		return "listAction";
	}
	
	
	/**
	 * 验证用户是否登录
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String isLogin() throws UnsupportedEncodingException{
		//处理验证码:判断验证码输入的是否正确
//		boolean flag=SessionUtils.isCheckNum(request);
//		if(!flag){
//			this.addFieldError("checkNum", "验证输入有误,请重新输入");
//			return "login";
//		}
		
	 	//处理用户名和密码输入的是否正确
  	    String name=request.getParameter("name");
  	    String password=request.getParameter("password");
  	    
  		MD5keyBean m = new MD5keyBean();
		password = m.getkeyBeanofStr(password);
		
		SysUser sysUser=sysUserService.findSysUserByNameAndPassword(name,password);
  	    
		//验证失败
		if(sysUser==null){
  	    	this.addFieldError("name", "用户名或者密码输入有误");
			return "login";
  	    }
  	    
  	    //登录成功,放置当前的对象到session中
		SessionUtils.setSysUserToSession(request,sysUser);
  	    
  	    //处理Cookie
		addCookie(name,request.getParameter("password"),response,request);
		
		return "main";
	}
	
	private void addCookie(String name, String password, HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException {
		if(StringUtils.isNotBlank(name)&&StringUtils.isNotBlank(password)){
			
			//创建Cookie
			Cookie nameCookie=new Cookie("name",java.net.URLEncoder.encode(name,"utf-8"));
			Cookie pswCookie=new Cookie("psw",password);
			
			//设置Cookie的父路径
			nameCookie.setPath(request.getContextPath()+"/");
			pswCookie.setPath(request.getContextPath()+"/");
			
			//获取是否保存Cookie
			String rememberMe=request.getParameter("rememberMe");
			if(rememberMe==null){//不保存Cookie
				nameCookie.setMaxAge(0);
				pswCookie.setMaxAge(0);
			}else{  //保存Cookie
				nameCookie.setMaxAge(7*24*60*60);
				pswCookie.setMaxAge(7*24*60*60);
			}
			
			//加入Cookie到响应头
			response.addCookie(nameCookie);
			response.addCookie(pswCookie);
		}
	}
	public SysUserForm getModel() {
		return sysUserForm;
	}
}
