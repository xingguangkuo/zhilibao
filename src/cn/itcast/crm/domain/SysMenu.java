package cn.itcast.crm.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SysMenu implements Serializable {
  /*
   * CREATE TABLE sys_menu
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
   */
	
	private SysMenuId id;
	private Integer sort;
	private String menuName;
	private String title;
	private String url;
	private String target;
	private String icon;
	private String remark;
	public SysMenuId getId() {
		return id;
	}
	public void setId(SysMenuId id) {
		this.id = id;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
