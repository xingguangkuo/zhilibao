package cn.itcast.crm.domain;

@SuppressWarnings("serial")
public class SysRole implements java.io.Serializable {
	private String id;
	private String name;
	private String remark;
	
	//一个角色含有多个用户,这里也不做配置
	//private Set roleUsers=new HashSet(0);

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
