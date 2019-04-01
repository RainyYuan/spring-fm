package com.kkb.spring.bean.definition;

public class RuntimeBeanReference {

	private String refName;

	public RuntimeBeanReference(String refName) {
		super();
		this.refName = refName;
	}

	public String getRefName() {
		return refName;
	}

	public void setRefName(String refName) {
		this.refName = refName;
	}
	
	
}
