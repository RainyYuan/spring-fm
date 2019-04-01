package com.kkb.spring.bean.reader;

import com.kkb.spring.bean.factory.DefaultListableBeanFactory;

public interface BeanDefinitionReader {
	void loadBeanDefinitions(String path, DefaultListableBeanFactory beanFactory);
}
