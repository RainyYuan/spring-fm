package com.kkb.spring.test;

import org.junit.Test;

import com.kkb.spring.bean.factory.DefaultListableBeanFactory;

public class TestSpringFramework {

	@Test
	public void test() {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory("beans.xml");
	}

}
