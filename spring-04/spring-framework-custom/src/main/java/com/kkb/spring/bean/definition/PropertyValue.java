package com.kkb.spring.bean.definition;

/***
 * 存储property标签的属性和值
 * 
 * @author think
 *
 */
public class PropertyValue {

	private String name;

	private Object value;

	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Object getValue() {
		return value;
	}


	public void setValue(Object value) {
		this.value = value;
	}


	public PropertyValue(String name, Object value) {
		super();
		this.name = name;
		this.value = value;
	}

}
