package com.kkb.spring.bean.definition;

import java.util.ArrayList;
import java.util.List;

public class BeanDefinition {

	private String name;

	private String beanClassName;
	
	public String getInitMethod() {
		return initMethod;
	}

	public void setInitMethod(String initMethod) {
		this.initMethod = initMethod;
	}

	private String initMethod;
	
	private List<PropertyValue> propertyValues = new ArrayList<>();

	public BeanDefinition(String beanClassName) {
		this(beanClassName, null);
	}

	public BeanDefinition(String beanClassName, String name) {
		super();
		this.beanClassName = beanClassName;
		this.name = name;
	}

	public String getBeanClassName() {
		return beanClassName;
	}

	public void setBeanClassName(String beanClassName) {
		this.beanClassName = beanClassName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PropertyValue> getPropertyValues() {
		return propertyValues;
	}

}
