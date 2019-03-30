package com.kkb.spring.bean.factory;

import java.util.HashMap;
import java.util.Map;

import com.kkb.spring.bean.definition.BeanDefinition;
import com.kkb.spring.bean.reader.XmlBeanDefinitionReader;

/***
 * 默认工厂实现类
 * 
 * @author think
 *
 */
public class DefaultListableBeanFactory extends AbstractBeanFactory {

	/**
	 * key是beanName，value是Object
	 */
	// private Map<String, Object> beans = new HashMap<>();
	/**
	 * key是beanName，value是BeanDefinition
	 */

	private Map<String, BeanDefinition> beanDefinitions = new HashMap<>();

	public DefaultListableBeanFactory(String path) {
		loadBeanDefinitions(path);
	}

	private void loadBeanDefinitions(String path) {
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader();
		// 加载BeanDefinition集合，并注册给工厂
		reader.loadBeanDefinitions(path, this);
	}

	public Map<String, BeanDefinition> getBeanDefinitions() {
		return beanDefinitions;
	}

	public void registerBeanDefinition(String key, BeanDefinition value) {
		this.beanDefinitions.put(key, value);
	}
}
