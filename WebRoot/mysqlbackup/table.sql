#############################################################################################################
#部门信息表
CREATE TABLE `sys_user_group` (
  `id` INTEGER(11) NOT NULL AUTO_INCREMENT,               #编号
  `remark` TEXT,                                          #备注
  `name` VARCHAR(100) DEFAULT NULL,                       #部门名称
  `principal` VARCHAR(50)  DEFAULT NULL,                  #部门负责人
  `incumbent` VARCHAR(200)  DEFAULT NULL,                 #部门职能
  PRIMARY KEY (`id`)
)
#############################################################################################################
#权限组表
CREATE TABLE `sys_role` (
  `id` varchar(36),                             #编号
  `remark` TEXT,                                #备注
  `name` VARCHAR(100)  DEFAULT NULL,             #名称
  PRIMARY KEY (`id`)
)
#############################################################################################################
 #系统用户表
CREATE TABLE `sys_user` (
  `id` INTEGER(11) NOT NULL AUTO_INCREMENT,                              #编号  
  `creator` VARCHAR(100)  DEFAULT NULL,                                  #创建人
  `createTime` VARCHAR(19) DEFAULT NULL,                                 #创建时间
  `updater` VARCHAR(100)  DEFAULT NULL,                                  #修改人
  `updateTime` VARCHAR(19) DEFAULT NULL,                                 #修改时间
  `remark` TEXT,                                                         #备注
  `name` VARCHAR(100)  DEFAULT NULL,                                     #用户名
  `cnname` VARCHAR(100) DEFAULT NULL,                                    #中文名
  `password` VARCHAR(100)  DEFAULT NULL,                                 #密码
  `address` VARCHAR(200)  DEFAULT NULL,                                  #家庭地址
  `telephone` VARCHAR(100)  DEFAULT NULL,                                #家庭电话
  `email` VARCHAR(100)  DEFAULT NULL,                                    #电子邮件
  `beginDate` DATE DEFAULT NULL,                                         #起始有效期
  `endDate` DATE DEFAULT NULL,                                           #终止有效期
  `roleId`  VARCHAR(36) NOT NULL,                                        #操作权限组 (外键##################)
  `groupId` INTEGER(11) NOT NULL DEFAULT '0',                            #所属部门   (外键##################)
  `accessFileLevel` VARCHAR(50)  DEFAULT NULL,                           #未命名
  `status` VARCHAR(10)  DEFAULT NULL,                                    #状态
  `commendMan` VARCHAR(50) DEFAULT NULL,                                 #推荐人
  `movetelePhone` VARCHAR(20)  DEFAULT NULL,                             #移动电话
  `nowAddress` VARCHAR(200)  DEFAULT NULL,                               #现住宅地址
  `nowtelePhone` VARCHAR(20)  DEFAULT NULL,                              #现住宅电话
  `identityCode` VARCHAR(20)  DEFAULT NULL,                              #身份证号码
  `insuranceCode` VARCHAR(20)  DEFAULT NULL,                             #社会保险号
  `instancyLinkman` VARCHAR(50)  DEFAULT NULL,                           #紧急联系人
  `instancytelePhone` VARCHAR(50)  DEFAULT NULL,                         #紧急联系电话
  `sex` VARCHAR(10)  DEFAULT NULL,                                       #性别
  `birthday` DATE DEFAULT NULL,                                          #出生日期
  `personnelType` VARCHAR(50)  DEFAULT NULL,                             #职员类别
  `duty` VARCHAR(50)  DEFAULT NULL,                                      #职务
  `workDate` DATE DEFAULT NULL,                                          #入职时间
  `highSchool` VARCHAR(100)  DEFAULT NULL,                               #最高学历
  `finishSchool` VARCHAR(100)  DEFAULT NULL,                             #毕业学校
  `finishSchoolDate` DATE DEFAULT NULL,                                  #毕业时间
  `consortName` VARCHAR(100)  DEFAULT NULL,                              #配偶姓名
  `youngoneName` VARCHAR(100)  DEFAULT NULL,                             #子女姓名
  `officetelePhone` VARCHAR(20)  DEFAULT NULL,                           #办公电话
  `consorttelePhone` VARCHAR(20)  DEFAULT NULL,                          #配偶电话
  `avocation` TEXT ,                                                     #业余爱好
  `consortCompany` VARCHAR(200)  DEFAULT NULL,                           #配偶工作单位
  `strongSuit` TEXT ,                                                    #偏好特长
  `commUniCate` TEXT ,                                                   #信息沟通
  `bringup` TEXT ,                                                       #培训情况                                                     
  `organise` TEXT ,                                                      #组织能力
  `analyse` TEXT ,                                                       #分析能力
  `planing` TEXT ,                                                       #计划能力
  `empolder` TEXT ,                                                      #人员开发
  `relation` TEXT,                                                       #人际关系
  PRIMARY KEY (`id`)
)

 #设置外键约束
 alter table sys_user
 add CONSTRAINT `sys_userfk_2` FOREIGN KEY (`groupId`) REFERENCES `sys_user_group` (`id`)
 
 
 #设置外键约束
 alter table sys_user
 add CONSTRAINT `sys_userfk_3` FOREIGN KEY (`roleId`) REFERENCES `sys_role` (`id`)
#############################################################################################################
#操作表
CREATE TABLE sys_popedom
(
   popedomModule       VARCHAR(30),                              #模块名称
   popedomPrivilege    VARCHAR(30),                              #操作名称
   sort                INTEGER(11),                              #排序
   title               VARCHAR(200),                             #提示
   popedomName         VARCHAR(200),                             #标题
   remark              TEXT,                                     #说明
   PRIMARY KEY(popedomModule,popedomPrivilege)
)
#############################################################################################################
#操作权限表
CREATE TABLE sys_popedom_privilege
(
   roleId         VARCHAR(36),        #权限组编号
   popedomModule     VARCHAR(30),     #模块名称
   popedomPrivilege  VARCHAR(30),     #操作名称
   PRIMARY KEY(roleId,popedomModule,popedomPrivilege)
)
#############################################################################################################
#菜单表
CREATE TABLE sys_menu
(
   menuModule     VARCHAR(30),                              #模块名称
   menuPrivilege  VARCHAR(30),                              #操作名称 
   sort           INTEGER(11),                              #排序字段              
   menuName       VARCHAR(200),                             #中文名称
   title          VARCHAR(200),                             #标题
   url            VARCHAR(200),                             #链接路径
   target         VARCHAR(20),                              #目标框架
   icon           VARCHAR(20),                              #使用的图标
   remark         TEXT,                                     #说明
   PRIMARY KEY(menuModule,menuPrivilege)
)
#############################################################################################################
#菜单权限表
CREATE TABLE sys_menu_privilege
(
   roleId         VARCHAR(36),     #权限组编号
   menuModule     VARCHAR(30),     #模块名称
   menuPrivilege  VARCHAR(30),     #操作名称
   PRIMARY KEY(roleId,menuModule,menuPrivilege)
)
#############################################################################################################
#客户信息表  
  CREATE TABLE `c_company` (
	  `id` int(11) NOT NULL auto_increment,             #编号
	  `code` varchar(50) default NULL,                  #客户编码
	  `name` varchar(100) NOT NULL,                     #客户名称
	  `pycode` varchar(100) default NULL,               #拼音码
	  `grade` varchar(100) default NULL,                #客户等级(下拉)
	  `regionName` varchar(100) default NULL,           #区域名称(东北  西北  华北)(下拉)
	  `source` varchar(100) default NULL,               #客户来源(下拉)
	  `trade` varchar(100) default NULL,                #所属行业(下拉)
	  `scale` varchar(100) default NULL,                #公司规模(下拉)
	  `province` varchar(50) default NULL,              #省份(下拉)
	  `city` varchar(50) default NULL,                  #城市(下拉)
	  `postcode` varchar(50) default NULL,              #邮政编码
	  `address` varchar(200) default NULL,              #联系地址
	  `email` varchar(100) default NULL,                #电子邮件
	  `web` varchar(200) default NULL,                  #公司网站
	  `tel1` varchar(50) default NULL,                  #电话一
	  `fax` varchar(50) default NULL,                   #传真
	  `mobile` varchar(50) default NULL,                #手机
	  `tel2` varchar(50) default NULL,                  #电话二
	  `nextTouchDate` DATE default NULL,                #下次联系时间
	  `quality` varchar(100) default NULL,              #客户性质(下拉)
	  `remark` text,                                    #备注
	  `dealin` varchar(100) default NULL,               #经营范围(下拉)
	  `kind` varchar(100) default NULL,                 #企业性质(下拉)
	  `artificialPerson` varchar(50) default NULL,      #法人代表
	  `registeMoney` varchar(50) default NULL,          #注册资金
	  `bank` varchar(100) default NULL,                 #开户银行
	  `account` varchar(100) default NULL,              #银行账户
	  `taxCode` varchar(100) default NULL,              #公司税号
	  `creater` varchar(50) default NULL,               #创建人    --本条记录的添加者(添加的用户名)
	  `createTime` varchar(19) default NULL,            #创建日期  ---
	  `updater` varchar(50) default NULL,               #修改人    --本条记录的修改者(中间可能发生多次变更)
	  `updateTime`  varchar(19) default NULL,           #修改日期  ---
	  
	  `ownerUser` int(11) NOT NULL,                     #所属人ID(外键)-----	  
	  `dispensePerson` varchar(50) default NULL,        #所属人名称  分配人(经手人变更给的人)----
	  `dispenseDate` varchar(19)   default NULL,        #分配日期(经手人变更的日期)-----
	  
	  `shareFlag` char(1) default NULL,                 #共享标志 Y(共享)和N(不共享)
	  `shareIds` varchar(500) default NULL,             #共享ID客户资料共享给业务人员 格式 #人事编号#人事编号...例如(#12#11#)
       PRIMARY KEY  (`id`)
)

#设置外键约束
alter table c_company
add CONSTRAINT `c_company_ibfk_2` FOREIGN KEY (`ownerUser`) REFERENCES `sys_user` (`id`)
#############################################################################################################
 #编码规则表
 CREATE TABLE sys_code_rule (
   id                INTEGER(11) NOT NULL AUTO_INCREMENT,    #编号 
   module            VARCHAR(20),                            #模块名称(固定) 
   areaPrefix        VARCHAR(20),                            #编码前缀(可变)
   areaTime          VARCHAR(20),                            #日期位(可变)
   glideBit          INTEGER(11),                            #流水位(可变)
   currentCode       VARCHAR(50),                            #预览(显示的提供看的)
   tabName           VARCHAR(50),                            #表名(固定) 
   available         CHAR(10),                               #是否有效(Y和N两个值,如果是保存是Y ,如果"#编码前缀,#日期位,#流水位中有一个发生变化则修改为Y)
   nextseq           VARCHAR(20),                            #下次产生的序号
   curDate           VARCHAR(10),                            #序号对应的日期(yyyyMMdd) 
   PRIMARY KEY (id)
 )
#############################################################################################################
#省份信息表
CREATE TABLE b_province (                                                                                            
   id      INT(11)       NOT NULL AUTO_INCREMENT  PRIMARY KEY,      #省编号                                                                          
   name    VARCHAR(100)  DEFAULT NULL,                 #省名称                                                                             
   pycode  VARCHAR(50)   DEFAULT NULL                  #拼音码
)

###########################################################################################################
#城市信息表
CREATE TABLE  b_city(                                                                                                  
   id  INT(11) NOT NULL AUTO_INCREMENT   PRIMARY KEY,      #城市编号                                                                          
   name  VARCHAR(100) DEFAULT NULL,                        #城市名称                                                                
   pycode  VARCHAR(50) DEFAULT NULL,                       #城市拼音码                                                             
   pid  INT(11) DEFAULT NULL,                              #省编号                                                              
   postcode  VARCHAR(50) DEFAULT NULL,                     #邮编                                                             
   areacode  VARCHAR(50) DEFAULT NULL                      #区号 
 );

ALTER TABLE b_city
ADD CONSTRAINT b_cityP_fk FOREIGN KEY(pid) REFERENCES b_province(id);
###########################################################################################################
 #类型信息表
CREATE TABLE `sys_dictionary_type` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,             #编号
  `sort` INT(11) DEFAULT NULL,                      #排序序号  该值初始值等id的值(上移 下移)********
  `remark` TEXT,                                    #备注
  `code`  VARCHAR(100) DEFAULT NULL,                 #类型唯一标识
  `value` VARCHAR(200) DEFAULT NULL,                #细节名称例如(东北,华北),
  `sysFlag` CHAR(1) DEFAULT NULL,                   #是否有效  'N' 表示删除掉    'Y' 可用
   PRIMARY KEY  (`id`)
  )
#############################################################################################################
--日志表(记录登陆用户的操作状态)
CREATE TABLE `sys_operate_log` (
  `id` int(11) NOT NULL auto_increment,      #编号
  `userName` varchar(100) default NULL,      #登陆用户的名称
  `cnname` varchar(100) default NULL,        #登陆用户的中文名称
  `actionType` varchar(20) default NULL,     #操作类型[添加  删除  编辑  修改  共享.....]
  `actionContent` text,                      #操作的内容  添加一个客户信息(ID:9,客户名称:上海开铺,客户编码:C-2011-0429-0013)
  `actionDate` varchar(30) default NULL,     #操作的日期
  PRIMARY KEY  (`id`)
)










