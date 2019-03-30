package com.kkb.spring.bean.parser;

import java.util.List;

import org.dom4j.Element;

import com.kkb.spring.bean.definition.BeanDefinition;
import com.kkb.spring.bean.definition.PropertyValue;
import com.kkb.spring.bean.definition.RuntimeBeanReference;
import com.kkb.spring.bean.definition.TypeStringValue;
import com.kkb.spring.bean.factory.DefaultListableBeanFactory;

public class BeanDefinitionParser {

	private DefaultListableBeanFactory beanFactory;

	public BeanDefinitionParser(DefaultListableBeanFactory beanFactory) {
		super();
		this.beanFactory = beanFactory;
	}

	/**
	 * 解析beans根标签
	 * 
	 * @param rootElement
	 */
	@SuppressWarnings("unchecked")
	public void parseRootElement(Element rootElement) {
		if (rootElement == null)
			return;

		// 获取所有的bean标签
		List<Element> elements = rootElement.elements();
		for (Element element : elements) {
			// 解析bean标签
			parseBeanElement(element);
		}

	}

	/**
	 * 解析bean标签
	 * 
	 * @param element
	 */
	@SuppressWarnings("unchecked")
	private void parseBeanElement(Element element) {
		if (element == null)
			return;
		// 获取id属性
		String id = element.attributeValue("id");
		// 获取class属性
		String clazz = element.attributeValue("class");

		String beanName = id;
		// 创建BeanDefinition对象
		BeanDefinition beanDefinition = new BeanDefinition(clazz, beanName);
		// 获取property子标签集合
		List<Element> propertyElements = element.elements();
		for (Element propertyElement : propertyElements) {
			parsePropertyElement(beanDefinition, propertyElement);
		}

		// 注册BeanDefinition信息
		registerBeanDefinition(beanName, beanDefinition);
	}

	/***
	 * 解析property子标签
	 * 
	 * @param beanDefinition
	 * 
	 * @param propertyElement
	 */
	private void parsePropertyElement(BeanDefinition beanDefinition, Element propertyElement) {
		if (propertyElement == null)
			return;

		// 获取name属性
		String name = propertyElement.attributeValue("name");
		// 获取value属性
		String value = propertyElement.attributeValue("value");
		// 获取ref属性
		String ref = propertyElement.attributeValue("ref");

		// 如果value和ref都有值，则返回
		if (value != null && !value.equals("") && ref != null && !ref.equals("")) {
			return;
		}

		PropertyValue pv = null;

		if (value != null && !value.equals("")) {
			// 因为spring配置文件中的value是String类型，而对象中的属性值是各种各样的，所以需要存储类型
			TypeStringValue typeStringValue = new TypeStringValue(value);

			pv = new PropertyValue(name, typeStringValue);
			beanDefinition.getPropertyValues().add(pv);
		} else if (ref != null && !ref.equals("")) {
			
			RuntimeBeanReference reference = new RuntimeBeanReference(ref);
			pv = new PropertyValue(name, reference);
			beanDefinition.getPropertyValues().add(pv);
		} else {
			return;
		}
	}

	/***
	 * 将解析之后的BeanDefinition信息，封装到BeanFactory中
	 * 
	 * @param bd
	 * @param beanName
	 */
	private void registerBeanDefinition(String beanName, BeanDefinition bd) {
		this.beanFactory.registerBeanDefinition(beanName, bd);
	}
}
