package cn.itcast.crm.web.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import cn.itcast.bean.CompanySearch;
import cn.itcast.crm.container.ServiceProvinder;
import cn.itcast.crm.domain.City;
import cn.itcast.crm.domain.Company;
import cn.itcast.crm.domain.Province;
import cn.itcast.crm.domain.SysDictionaryType;
import cn.itcast.crm.domain.SysUser;
import cn.itcast.crm.domain.SysUserGroup;
import cn.itcast.crm.service.ICityService;
import cn.itcast.crm.service.ICompanyService;
import cn.itcast.crm.service.IProvinceService;
import cn.itcast.crm.service.ISysDictionaryTypeService;
import cn.itcast.crm.service.ISysUserGroupService;
import cn.itcast.crm.service.ISysUserService;
import cn.itcast.crm.util.DataType;
import cn.itcast.crm.util.Global;
import cn.itcast.crm.util.PingyinUtils;
import cn.itcast.crm.util.SQLDateConverter;
import cn.itcast.crm.util.SessionUtils;
import cn.itcast.crm.web.form.CompanyForm;

import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class CompanyAction extends BaseAction implements ModelDriven<CompanyForm> {
	
	private CompanyForm companyForm=new CompanyForm();
	
	//获取客户的业务层
	private ICompanyService companyService=
		    (ICompanyService)ServiceProvinder.getService(ICompanyService.SERVICE_NAME);
	
	//获取处理下拉选得业务层
	private ISysDictionaryTypeService sysDictionaryTypeService=
		    (ISysDictionaryTypeService)ServiceProvinder.getService(ISysDictionaryTypeService.SERVICE_NAME);
	
	//获取省得业务层
	private IProvinceService provinceService=
		    (IProvinceService)ServiceProvinder.getService(IProvinceService.SERVICE_NAME);
	
	//获取城市的业务层
	private ICityService cityService=
		    (ICityService)ServiceProvinder.getService(ICityService.SERVICE_NAME);
	
	//获取部门的业务层
	private ISysUserGroupService sysUserGroupService=
	    (ISysUserGroupService)ServiceProvinder.getService(ISysUserGroupService.SERVICE_NAME);
	
	//获取用户的业务层
	private ISysUserService sysUserService=
	    (ISysUserService)ServiceProvinder.getService(ISysUserService.SERVICE_NAME);

	/**
	 * 变更所属人
	 * @return
	 */
	public String changeHandler(){
		//获取客户id
        String ids=request.getParameter("ids");
        if(StringUtils.isNotBlank(ids)){
       	   String sid[]=ids.split(",");
       	   Integer id[]=DataType.converterStringArray2IntegerArray(sid);
        
       	   //获取要变更给的用户的id
       	   String snew_owner=request.getParameter("new_owner");
       	   if(StringUtils.isNotBlank(snew_owner)){
       		  Integer new_owner=Integer.parseInt(snew_owner.trim());
       		  companyService.changeHandler(id,new_owner);
       		  return "changeHandler";
       	   }
        }
		return null;
	}
	
	/**
	 * 显示经手人变更页面
	 * @return
	 */
	public String showChangePerson(){
		//获取系统的用户
		List<SysUser> sysUserSelect=sysUserService.findAllSysUser();
		request.setAttribute("sysUserSelect", sysUserSelect);
		return "showChangePerson";
	}
	
	/**
	 * 显示下次联系时间页面
	 * @return
	 */
	public String showNextTouchTime(){
		//request.getParameterValues("ids")[0]		
		//<result name="showNextTouchTime">/WEB-INF/page/crm/customer/base/nextTouchTime.jsp</result>
		
		return "showNextTouchTime";
	}
	
	/**
	 * 修改下次联系时间
	 * @return
	 */
	public String updateNextTouchTime(){
		//获取客户id
         String ids=request.getParameter("ids");
         if(StringUtils.isNotBlank(ids)){
        	 String sid[]=ids.split(",");
        	 Integer id[]=DataType.converterStringArray2IntegerArray(sid);
        	 
        	 
        	//获取下次联系时间
        	 String snext_touch_date=request.getParameter("next_touch_date");
        	 java.sql.Date next_touch_date=java.sql.Date.valueOf(snext_touch_date);
        	 companyService.updateNextTouchTime(id,next_touch_date);
        	 
        	 return "updateNextTouchTime";        	 
        	 
         }
		return  null;
	}
	
	
	/**
	 * 显示查看共享页面
	 * @return
	 */
	public String showShareViewOne(){
        String sid=request.getParameter("id");
		if(StringUtils.isNotBlank(sid)){
			Integer id=Integer.parseInt(sid.trim());
			Company company=companyService.findCompanyById(id); 		    
		    request.setAttribute("company", company);
		    
		    //获取该客户共享的用户信息
		    List<SysUser> sysUsers=companyService.findSysUsersBySharedIds(id);
		    request.setAttribute("sysUsers", sysUsers);
		    
		    return "showShareViewOne";
		}
		
		return null;
		
	}
	
	/**
	 * 显示取消共享页面
	 * @return
	 */
	public String showShareCancelOne(){
		//获取客户id
		String sid=request.getParameter("id");
		
		if(StringUtils.isNotBlank(sid)){
			Integer id=Integer.parseInt(sid.trim());
			Company company=companyService.findCompanyById(id); 		    
		    request.setAttribute("company", company);
		
		    // <result name="showShareCancelOne">/WEB-INF/page/crm/customer/base/shareCancelOne.jsp</result>
		    return "showShareCancelOne";
		}
		
		return null;
	}
	
	/**
	 * 取消共享设置
	 * @return
	 */
	public String updateshareCancelOne(){
		//获取客户id
        String sid=request.getParameter("id");
		if(StringUtils.isNotBlank(sid)){
			 Integer id=Integer.parseInt(sid.trim());
			 //获取模块名称
	         String s_module=request.getParameter("s_module");
             if(StringUtils.isNotBlank(s_module)){			
	            companyService.updateshareCancelOne(id,s_module);
	            return "updateshareCancelOne";
             }
		}
		return null;
	}
	
	/**
	 * 显示增加共享和减少共享页面
	 * @return
	 */
	public String showShareSetOne(){
		//获取客户id
		String sid=request.getParameter("id");
		if(StringUtils.isNotBlank(sid)){
			Integer id=Integer.parseInt(sid.trim());
			Company company=companyService.findCompanyById(id); 		    
		    request.setAttribute("company", company);
		    
		    //获取部门的信息
		    List<SysUserGroup> sysUserGroups=sysUserGroupService.findAllSysUserGroups();
		    request.setAttribute("sysUserGroups", sysUserGroups);
		    
		    //获取用户的信息
		    List<SysUser> sysUsers=sysUserService.findAllSysUser();
		    request.setAttribute("sysUsers", sysUsers);
		    
		    return "showShareSetOne";
		}
		return null;
	}
	
	/**
	 * 修改共享设置
	 * @return
	 */
	public String updateShareSetOne(){
		//获取客户id
		 String sid=request.getParameter("id");
		 if(StringUtils.isNotBlank(sid.trim())){
		     Integer id=Integer.parseInt(sid.trim());
			 //获取模块名称
	         String s_module=request.getParameter("s_module");		
			 if(StringUtils.isNotBlank(s_module)){
			    //获取用户id
		         String[] suids=request.getParameterValues("uid");
		         Integer  uids[]=DataType.converterStringArray2IntegerArray(suids);
		          
		         /*
		          * 	<select name='sharetype' id='sharetype'>
								<option value='add'>增加共享</option>
								<option value='minus'>减少共享</option>
						</select>
		          */
		         String sharetype=request.getParameter("sharetype");
		         if(StringUtils.isNotBlank(sharetype)){
		             if("add".equals(sharetype)){  //增加共享
		            	  companyService.addUpdateShareSetOne(s_module,id,uids);
		             }
					
		             if("minus".equals(sharetype)){   //减少共享
						  companyService.minusUpdateShareSetOne(s_module,id,uids);	            	 
				     }
		         }
		         return "updateShareSetOne";
			 }
		 }
		 return null;
	}
	
	
	public String  edit() throws IllegalAccessException, InvocationTargetException{
		//处理客户信息添加的下拉选
		//处理客户等级的下拉选
		List<SysDictionaryType>  gradesSelect=sysDictionaryTypeService.findSysDictionaryTypeByCode(Global.GRADE);
		request.setAttribute("gradesSelect", gradesSelect);
		
		//处理区域名称
		List<SysDictionaryType> regionNamesSelect=sysDictionaryTypeService.findSysDictionaryTypeByCode(Global.REGIONNAME);
		request.setAttribute("regionNamesSelect", regionNamesSelect);
		
		//获取所有的省得信息
		List<Province> provincesSelect=provinceService.findAllProvinces();	
		request.setAttribute("provincesSelect", provincesSelect);
		
		//获取客户的id
		String sid=request.getParameter("id");
		if(StringUtils.isNotBlank(sid)){
			//通过客户的id获取客户的信息
			Integer id=Integer.parseInt(sid.trim());			
			Company company=companyService.findCompanyById(id);
			//复制客户的值到vo中
			BeanUtils.copyProperties(companyForm, company);
		
			//特殊处理
			String pname=company.getProvince();
			
			Province province=provinceService.findProvinceByName(pname);
		    if(province!=null){
		    	//通过省的id查询该省对应的城市信息
		    	List<City> citiesSelect=cityService.findCitiesByPid(province.getId());
		    	request.setAttribute("citiesSelect", citiesSelect);
		    }
		    
		    if(company.getSysUser()!=null){
		        //处理所属人id
			    companyForm.setOwnerUser(company.getSysUser().getId()+"");
			    //处理所属人的日期
		    }
	        return "edit";
		}
	 return null;
	}
	
	
	/**
	 * 显示省下对应的城市
	 * @return
	 * @throws IOException 
	 */
	public String showCity() throws IOException{
		//获取省得名称
		String name=request.getParameter("name");
		if(StringUtils.isNotBlank(name)){
		   //通过省得名称获取省的id
			Province province=provinceService.findProvinceByName(name);
		    if(province!=null){
		    	//通过省的id查询该省对应的城市信息
		    	List<City> citiesSelect=cityService.findCitiesByPid(province.getId());
		    	
		    	JsonConfig config=new JsonConfig();
		    	config.setExcludes(new String[]{"id","pid","pycode","postcode","areacode"});
		    	
		    	JSONArray jsonArray=JSONArray.fromObject(citiesSelect,config);
            	System.out.println(jsonArray.toString());
            	response.setCharacterEncoding("utf-8");
            	response.getWriter().print(jsonArray.toString());
		    }
		}
		
	   return null;
	}
	
	public String save() throws IllegalAccessException, InvocationTargetException{
	    //实例化po
		Company company=new Company();
		
		//注册转化器
		ConvertUtils.register(new SQLDateConverter(), java.sql.Date.class);
		
		//赋值vo的值到po
		BeanUtils.copyProperties(company, companyForm);
		
		//处理特殊情况
		//处理客户的所有人的id
		String userid=companyForm.getOwnerUser();
		if(StringUtils.isNotBlank(userid)){
			SysUser sysUser=new SysUser();
			sysUser.setId(Integer.parseInt(userid.trim()));
			company.setSysUser(sysUser);
	
			//设置分配给所属人人的日期
			company.setDispenseDate(companyForm.getCreateTime());
			company.setShareFlag('N');
			
			
			//保存
	 		//获取当前的登陆用户
	         SysUser curSysUser=SessionUtils.getSysUserFormSession(request);
	         if(curSysUser!=null){
			     companyService.saveCompany(curSysUser,company);
			     return "listAction";
	         }
		}
         return null;
	}
	
	
	public String update() throws IllegalAccessException, InvocationTargetException{
	    //实例化po
		Company company=new Company();
		
		//注册转化器
		ConvertUtils.register(new SQLDateConverter(), java.sql.Date.class);
		
		//赋值vo的值到po
		BeanUtils.copyProperties(company, companyForm);
		
		//处理特殊情况
		
		//处理客户的所有人的id
		String userid=companyForm.getOwnerUser();
		if(StringUtils.isNotBlank(userid)){
			SysUser sysUser=new SysUser();
			sysUser.setId(Integer.parseInt(userid.trim()));
			company.setSysUser(sysUser);
			company.setShareFlag('N');

			//保存
	 		//获取当前的登陆用户
	         SysUser curSysUser=SessionUtils.getSysUserFormSession(request);
	         if(curSysUser!=null){
			     companyService.updateCompany(curSysUser,company);
			     return "listAction";
	         }
		}
         return null;
	}
	
	
	
	/**
	 * 显示客户添加页面
	 * @return
	 */
	public String add(){
        //处理客户的编码	
		String code=companyService.getCompanyCodeByTabName("c_company");
		request.setAttribute("code", code);
		
		//处理客户信息添加的下拉选
		//处理客户等级的下拉选
		List<SysDictionaryType>  gradesSelect=sysDictionaryTypeService.findSysDictionaryTypeByCode(Global.GRADE);
		request.setAttribute("gradesSelect", gradesSelect);
		
		//处理区域名称
		List<SysDictionaryType> regionNamesSelect=sysDictionaryTypeService.findSysDictionaryTypeByCode(Global.REGIONNAME);
		request.setAttribute("regionNamesSelect", regionNamesSelect);
		
		//获取所有的省得信息
		List<Province> provincesSelect=provinceService.findAllProvinces();	
		request.setAttribute("provincesSelect", provincesSelect);
		
		//获取当前登陆用户
		SysUser curSysuser=SessionUtils.getSysUserFormSession(request);
		if(curSysuser!=null){
		   //处理创建人 修改人  所属人  所属人id  创建日期  修改日期
			companyForm.setCreater(curSysuser.getCnname());
			companyForm.setUpdater(curSysuser.getCnname());
			companyForm.setDispensePerson(curSysuser.getCnname());
			companyForm.setOwnerUser(curSysuser.getId()+"");
			String curDate=DateFormatUtils.format(new java.util.Date(), "yyyy-MM-dd HH:mm:ss");
			companyForm.setCreateTime(curDate);
			companyForm.setUpdateTime(curDate);
			
			
			return "add";
		}
		return null;
	}
	
	public String pinyin() throws IOException{
		//获取客户的名称
		String name=request.getParameter("name");
		if(StringUtils.isNotBlank(name)){
			String pin=PingyinUtils.converterToFirstSpell(name.trim());
			response.getWriter().println(pin);
		}
		return null;
	}
	
	public String list(){
		//处理查询条件的下拉选
		//处理客户等级的下拉选
		List<SysDictionaryType>  gradesSelect=sysDictionaryTypeService.findSysDictionaryTypeByCode(Global.GRADE);
		request.setAttribute("gradesSelect", gradesSelect);
		
		//处理客户来源
		List<SysDictionaryType>  sourcesSelect=sysDictionaryTypeService.findSysDictionaryTypeByCode(Global.SOURCE);
		request.setAttribute("sourcesSelect", sourcesSelect);
		
		//处理客户性质
		List<SysDictionaryType>  qualitySelect=sysDictionaryTypeService.findSysDictionaryTypeByCode(Global.QUALITY);
		request.setAttribute("qualitySelect", qualitySelect);
		
		//实例化
		CompanySearch companySearch=new CompanySearch();
		companySearch.setCode(companyForm.getCode());
		companySearch.setName(companyForm.getName());
		companySearch.setPycode(companyForm.getPycode());
		companySearch.setGrade(companyForm.getGrade());
		companySearch.setSource(companyForm.getSource());
		companySearch.setQuality(companyForm.getQuality());
		companySearch.setTel1(companyForm.getTel1());
		
		SysUser curSysuser=SessionUtils.getSysUserFormSession(request);
		if(curSysuser!=null){
			List<Company> companyList=companyService.findCompanysCondition(curSysuser,companySearch);
			request.setAttribute("companyList", companyList);
			return "list";
		}
		return null;
	}

	public String delete(){
		String[] sids=request.getParameterValues("ids");
		if(sids!=null&&sids.length>0){
			Integer ids[]=DataType.converterStringArray2IntegerArray(sids);
			companyService.deleteCompanyByIds(ids);
			return "listAction";
		}
		return null;
	}
	
	
	public CompanyForm getModel() {
		return companyForm;
	}
}
