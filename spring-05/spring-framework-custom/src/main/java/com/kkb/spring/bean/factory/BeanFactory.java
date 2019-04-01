package com.kkb.spring.bean.factory;

public interface BeanFactory {

	Object getBean(String beanName);
	Object getBean(Class<?> beanType);
}
