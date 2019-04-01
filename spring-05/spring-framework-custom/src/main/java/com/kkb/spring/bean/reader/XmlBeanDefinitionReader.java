package com.kkb.spring.bean.reader;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.Element;

import com.kkb.spring.bean.factory.DefaultListableBeanFactory;
import com.kkb.spring.bean.parser.BeanDefinitionParser;
import com.kkb.spring.bean.resource.ClasspathResource;

/**
 * 通过读取xml中的bean标签，获取BeanDefinition对象
 * 
 * @author think
 *
 */
public class XmlBeanDefinitionReader implements BeanDefinitionReader {

	public void loadBeanDefinitions(String path,DefaultListableBeanFactory beanFactory) {
		//第一步：获取spring配置文件对应的Resource信息
		ClasspathResource resource = new ClasspathResource(path);
		//第二步：获取DocumentReader，创建Document对象
		DocumentReader documentReader = new DefaultDocumentReader();
		InputStream inputStream = resource.getInputStream();
		Document document = documentReader.loadDocument(inputStream);
		
		//第三步：通过BeanDefinitionParser去解析spring bean标签
		BeanDefinitionParser parser = new BeanDefinitionParser(beanFactory);
		Element rootElement = document.getRootElement();
		// 将解析之后的BeanDefinition信息，存储到BeanFactory
		parser.parseRootElement(rootElement);
	}
}
