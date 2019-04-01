package com.kkb.spring.bean.definition;

public class TypeStringValue {

	private String value;
	
	private Object targetType;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Object getTargetType() {
		return targetType;
	}

	public void setTargetType(Object targetType) {
		this.targetType = targetType;
	}

	public TypeStringValue(String value) {
		super();
		this.value = value;
	}
	
	
}
