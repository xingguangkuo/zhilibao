package cn.itcast.crm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bean.CompanySearch;
import cn.itcast.crm.dao.ICompanyDao;
import cn.itcast.crm.dao.ISysCodeRuleDao;
import cn.itcast.crm.dao.ISysOperateLogDao;
import cn.itcast.crm.dao.ISysUserDao;
import cn.itcast.crm.domain.Company;
import cn.itcast.crm.domain.SysCodeRule;
import cn.itcast.crm.domain.SysOperateLog;
import cn.itcast.crm.domain.SysUser;
import cn.itcast.crm.service.ICompanyService;
import cn.itcast.crm.util.DataType;

@Transactional(readOnly=true)
@Service(ICompanyService.SERVICE_NAME)
public class CompanyServiceImpl implements ICompanyService {

	@Resource(name=ISysCodeRuleDao.SERVICE_NAME)
	private ISysCodeRuleDao sysCodeRuleDao;
	
	@Resource(name=ICompanyDao.SERVICE_NAME)
	private  ICompanyDao  companyDao;
	
	@Resource(name=ISysUserDao.SERVICE_NAME)
	private  ISysUserDao  sysUserDao;
	
	@Resource(name=ISysOperateLogDao.SERVICE_NAME)
	private ISysOperateLogDao sysOperateLogDao;
	
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public String getCompanyCodeByTabName(String tabName) {
		
		//获取代码规则,查询sys_code_rule
		String whereHql=" and o.tabName=?";
		Object[] params={tabName};
		List<SysCodeRule> list=sysCodeRuleDao.findObjectsByConditionWithNoPage(whereHql, params);
		if(list==null||list.size()!=1){
			throw new RuntimeException("不能生成客户的编码");
		}
		SysCodeRule sysCodeRule=list.get(0);
		
		//* 获取 是否被修改过或新添加的 字段的值  
	    //* 如果  是否被修改过或新添加的=='Y'  
		if(sysCodeRule.getAvailable().equals("Y")){
		          //* 获取 流水位=3
		          Integer glideBit=sysCodeRule.getGlideBit();
			      //* 生成第一个流水号 001
		          String firstGlideNumber=DataType.geneFirstGlideNumber(glideBit);
		          //* 计算下一个流水号 002
		          String nextGlideNumber=DataType.geneNextGlideNumber(firstGlideNumber);
		          
		          //* 获取系统的当前时间 格式yyyyMMdd  20110914
		          String curDate=DateFormatUtils.format(new Date(), "yyyyMMdd");

		          //* 生成客户编码
		              //* 编码前缀+"-"+利用日期位格式生成当前的日期[yyyy-MM-dd ]+"-"+001  c-2011-09-14-001
                     String code=sysCodeRule.getAreaPrefix()+"-"
                              +DateFormatUtils.format(new Date(), sysCodeRule.getAreaTime())+"-"+firstGlideNumber;
		          
		          //* 修改代码规则表
		             //*  下一个序列号="002"
                     sysCodeRule.setNextseq(nextGlideNumber);
		             //*  当前日期  20110914
                     sysCodeRule.setCurDate(curDate);
		             //*  是否被修改过='N' 
                     sysCodeRule.setAvailable("N");
                     sysCodeRuleDao.update(sysCodeRule);
                return code;
		}else{  //是否被修改过或新添加的=='N'  
			  //* 获取代码规则表中的当前日期字段的值
	           String curDate=sysCodeRule.getCurDate();
			
			   //* 获取系统的当前日期、
               String sysCurDate=DateFormatUtils.format(new Date(), "yyyyMMdd"); 
               //* 如果代码规则表中的当前日期字段的值==系统的当前日期、
	           if(curDate.equals(sysCurDate)) {
	                    //* 获取下一个序列号 ="002"
	        	         String nextseq=sysCodeRule.getNextseq();
	        	   
	                    //* 计算新的流水号 003
	        	         String nextGlideNumber=DataType.geneNextGlideNumber(nextseq);
	        	         
	        	         //* 生成客户编码
	                       //* 编码前缀+"-"+利用日期位格式生成当前的日期[yyyy-MM-dd ]+"-"+001 
	        	         String code=sysCodeRule.getAreaPrefix()+"-"
                                     +DateFormatUtils.format(new Date(), sysCodeRule.getAreaTime())+"-"+nextseq;
	                     //* 修改代码规则表
	                     //*  下一个序列号="003"
	        	         sysCodeRule.setNextseq(nextGlideNumber);
	                     //*  当前日期  20110914
	                     //*  是否被修改过='N' 
	        	         sysCodeRuleDao.update(sysCodeRule);
	        	         return code;
	           }else{ //如果代码规则表中的当前日期字段的值!=系统的当前日期、
	        	   
	        	    //* 获取 流水位=3   
	        	    Integer glideBit=sysCodeRule.getGlideBit();
	                //* 生成第一个流水号 001
	        	    String firstGlideNumber=DataType.geneFirstGlideNumber(glideBit);
	                //* 计算下一个流水号 002
	        	    String nextGlideNumber=DataType.geneNextGlideNumber(firstGlideNumber);
	                //* 生成客户编码
	                  //* 编码前缀+"-"+利用日期位格式生成当前的日期[yyyy-MM-dd ]+"-"+001 
	        	    
	        	    String code=sysCodeRule.getAreaPrefix()+"-"
                    +DateFormatUtils.format(new Date(), sysCodeRule.getAreaTime())+"-"+firstGlideNumber;
	                
	        	    //* 修改代码规则表
	                    //*  下一个序列号="002"
	        	         sysCodeRule.setNextseq(nextGlideNumber);
	                    //*  当前日期  20110915
	        	         sysCodeRule.setCurDate(sysCurDate);
	                    //*  是否被修改过='N' 
	        	         sysCodeRule.setAvailable("N");
	        	        sysCodeRuleDao.update(sysCodeRule);
	        	        return code;
	           }
		}
	}
	
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveCompany(SysUser curSysUser, Company company) {
		if(curSysUser!=null&&company!=null){
			companyDao.save(company);
			
			//处理日志:写日志到数据库
			/*
			 * CREATE TABLE `sys_operate_log` (
				  `id` INT(11) NOT NULL AUTO_INCREMENT,      #编号
				  `userName` VARCHAR(100) DEFAULT NULL,      #登陆用户的名称
				  `cnname` VARCHAR(100) DEFAULT NULL,        #登陆用户的中文名称
				  `actionType` VARCHAR(20) DEFAULT NULL,     #操作类型[添加  删除  编辑  修改  共享.....]
				  `actionContent` TEXT,                      #操作的内容  添加一个客户信息(ID:9,客户名称:上海开铺,客户编码:C-2011-0429-0013)
				  `actionDate` VARCHAR(30) DEFAULT NULL,     #操作的日期
			  PRIMARY KEY  (`id`)
			)
			 */
			SysOperateLog log=new SysOperateLog();
			log.setUserName(curSysUser.getName());
			log.setCnname(curSysUser.getCnname());
			log.setActionType("添加");
			String actionContent="添加一个客户信息[ID:"+company.getId()+",客户名称:"+company.getName()+",客户编码:"+company.getCode()+"]";
			log.setActionContent(actionContent);
			log.setActionDate(DateFormatUtils.format(new java.util.Date(),"yyyy-MM-dd HH:mm:ss"));
			sysOperateLogDao.save(log);
		}
		
	}

	@SuppressWarnings("unchecked")
	public List<Company> findCompanysCondition(SysUser curSysuser,CompanySearch companySearch) {
		if(curSysuser!=null&&companySearch!=null){
			String whereHql="";
			List paramsList=new ArrayList();
			if(curSysuser.getId()!=null){
				whereHql=whereHql+" and o.sysUser.id=?";
				paramsList.add(curSysuser.getId());
			}
			
			if(StringUtils.isNotBlank(companySearch.getCode())){
				whereHql=whereHql+" and o.code like ?";
				paramsList.add("%"+companySearch.getCode().trim()+"%");
			}
			
			if(StringUtils.isNotBlank(companySearch.getName())){
				whereHql=whereHql+" and o.name like ?";
				paramsList.add("%"+companySearch.getName().trim()+"%");
			}
			
			if(StringUtils.isNotBlank(companySearch.getPycode())){
				whereHql=whereHql+" and o.pycode  like ?";
				paramsList.add("%"+companySearch.getPycode().trim()+"%");
			}
			
			if(StringUtils.isNotBlank(companySearch.getTel1())){
				whereHql=whereHql+" and o.tel1 like ?";
				paramsList.add("%"+companySearch.getTel1().trim()+"%");
			}
			
			if(StringUtils.isNotBlank(companySearch.getSource())){
				whereHql=whereHql+" and o.source like ?";
				paramsList.add("%"+companySearch.getSource().trim()+"%");
			}
			
			if(StringUtils.isNotBlank(companySearch.getGrade())){
				whereHql=whereHql+" and o.grade like ?";
				paramsList.add("%"+companySearch.getGrade().trim()+"%");
			}
			
			if(StringUtils.isNotBlank(companySearch.getQuality())){
				whereHql=whereHql+" and o.quality like ?";
				paramsList.add("%"+companySearch.getQuality().trim()+"%");
			}
			
			LinkedHashMap<String, String> orderby=new LinkedHashMap<String, String>();
			orderby.put("o.id", "asc");
			return companyDao.findObjectsByConditionWithNoPage(whereHql, paramsList.toArray(), orderby);
		}
		return null;
	}

	public Company findCompanyById(Integer id) {
		return companyDao.findObjectById(id);
	}
	
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void updateCompany(SysUser curSysUser, Company company) {
		companyDao.update(company);
	}
	
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void deleteCompanyByIds(Integer[] ids) {
		companyDao.deleteByIds((java.io.Serializable[])ids);
	}
	
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void addUpdateShareSetOne(String s_module, Integer id, Integer[] uids) {
	  if(StringUtils.isNotBlank(s_module)&&id!=null&&uids!=null&&uids.length>0){
		 if("c_company".equals(s_module)){
			   Company company=companyDao.findObjectById(id);
			   if(company!=null){
				  //处理用户 1#2#3# 
				   StringBuffer buf=new StringBuffer();
				  for(int i=0;i<uids.length;i++){
					  buf.append(uids[i]+"#");
				  }
				  
				  if('N'==company.getShareFlag()){
					  company.setShareFlag('Y');
					  company.setShareIds("#"+buf.toString());
					  companyDao.update(company);
				  }else{
					  company.setShareFlag('Y');
					  company.setShareIds(company.getShareIds()+buf.toString());
					  companyDao.update(company);
				  }				   
			   }
		 }
		  
	  }
	}

	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void minusUpdateShareSetOne(String s_module, Integer id,Integer[] uids) {
		 if(StringUtils.isNotBlank(s_module)&&id!=null&&uids!=null&&uids.length>0){
			if("c_company".equals(s_module)){
				 Company company=companyDao.findObjectById(id);
				   if(company!=null){
					   //N不处理
					   if('Y'==company.getShareFlag()){  //处理
						   //获取数据库中的值
						   String shareIds=company.getShareIds();
						   for(int i=0;i<uids.length;i++){ 
							   String uid="#"+uids[i]+"#";
							  
							   while(true){
									 if(shareIds!=null&&shareIds.contains(uid)){
										 shareIds=shareIds.replaceAll(uid, "#");
									 }else{
										 break;
									 }
							   }
						   }
							   
						  if("#".equals(shareIds)){
							 company.setShareFlag('N');
							 company.setShareIds(null);
							 companyDao.update(company);
						  }else{
							 company.setShareIds(shareIds);
							 companyDao.update(company);
						  }
					   }
				   }
		    }
		  }
	}

	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void updateshareCancelOne(Integer id, String s_module) {
        if(id!=null&&StringUtils.isNotBlank(s_module)){
        	 Company company=companyDao.findObjectById(id);
        	 if(company!=null){
        		 company.setShareFlag('N');
        		 company.setShareIds(null);
        		 companyDao.update(company);
        	 }
        }

	}

	public List<SysUser> findSysUsersBySharedIds(Integer id) {
		if(id!=null){
			 Company company=companyDao.findObjectById(id);
			 if(company!=null){
				 //#4#5#7#  
				 String shareIds=company.getShareIds();
				 if(StringUtils.isNotBlank(shareIds)){
					 String[] shareId=shareIds.split("#");
					 
					 //存放的用户的id
					 Integer uid[]=DataType.converterStringArray2IntegerArray(shareId);
					 if(uid!=null&&uid.length>0){
					    
						 StringBuffer whereBuf=new StringBuffer();
						 whereBuf.append(" and o.id in(");
						 List paramsList=new ArrayList();
						 
						//select o from SysUser o where  o.id in(?,?,?)
					    for(int i=1;i<uid.length;i++){
					    	whereBuf.append("?,");
					    	paramsList.add(uid[i]);
					    }
					    whereBuf.deleteCharAt(whereBuf.length()-1);
					    whereBuf.append(")");
					    
					    return sysUserDao.findObjectsByConditionWithNoPage(whereBuf.toString(), paramsList.toArray());
					 }
				 }
			 }
		}
		return null;
	}

	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void updateNextTouchTime(Integer[] id, java.sql.Date next_touch_date) {
		if(id!=null&&id.length>0&&next_touch_date!=null){
			for(int i=0;i<id.length;i++){
				Company company=companyDao.findObjectById(id[i]);
				if(company!=null){
					company.setNextTouchDate(next_touch_date);
					companyDao.update(company);
				}
			}
		}
	}

	
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void changeHandler(Integer[] id, Integer new_owner) {
		if(id!=null&&id.length>0&&new_owner!=null){
			//通过用户的id查询用户信息
			SysUser sysUser=sysUserDao.findObjectById(new_owner);
			for(int i=0;i<id.length;i++){
				//通过客户id查询客户信息
				Company company=companyDao.findObjectById(id[i]);
				if(company!=null&&sysUser!=null){
					company.setSysUser(sysUser);
					company.setDispensePerson(sysUser.getCnname());
					company.setDispenseDate(DateFormatUtils.format(new java.util.Date(), "yyyy-MM-dd HH:mm:ss"));
					companyDao.update(company);
				}
		   }
	     }
	}
}
