package com.hawk.demo.ztree.form;


import com.hawk.demo.ztree.model.DepartMent;

public class DeptForm {
	private Integer id;
	private String name;
	private DepartMent parent;
	private Integer pId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public DepartMent getParent() {
		return parent;
	}
	public void setParent(DepartMent parent) {
		this.parent = parent;
	}
	
	public Integer getpId() {
		return pId;
	}
	public void setpId(Integer pId) {
		this.pId = pId;
	}
	
	
}
