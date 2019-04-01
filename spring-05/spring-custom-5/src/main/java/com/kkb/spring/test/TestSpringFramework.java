package com.kkb.spring.test;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Test;

import com.kkb.spring.bean.factory.DefaultListableBeanFactory;
import com.kkb.spring.po.Course;
import com.kkb.spring.po.Student;

public class TestSpringFramework {

	@Test
	public void test() {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory("beans.xml");
		// Course course = (Course) beanFactory.getBean("course");
		Student student = (Student) beanFactory.getBean("student");
		System.out.println(student.getName());
		System.out.println(student.getCourse().getName());
		System.out.println(student.getCourse().getAge());
	}

	@Test
	public void test1() throws Exception {
		// 获取类对象
		String beanClassName = "com.kkb.spring.po.Course";
		Class<?> clazz = Class.forName(beanClassName);

		// 获取所有的方法(包含私有的方法)
		Method[] declaredMethods = clazz.getDeclaredMethods();
		// 获取指定名称和参数的public方法
		Method method = clazz.getDeclaredMethod("setName", String.class);

		// 得到所有的成员变量（属性）
		Field[] fields = clazz.getDeclaredFields();
		// 得到指定名称的成员变量（属性）(包含私有的属性)
		Field field = clazz.getDeclaredField("name");
		// 得到属性的类型
		Class<?> type = field.getType();
		System.out.println(field + "--" + type);
	}

	@Test
	public void test2() throws Exception {
		// 获取类对象
		String beanClassName = "com.kkb.spring.po.Course";
		Class<?> clazz = Class.forName(beanClassName);
		PropertyDescriptor pd = new PropertyDescriptor("name", clazz);
		Class<?> type = pd.getPropertyType();

		// 获取对应属性的set方法
		Method writeMethod = pd.getWriteMethod();
		// 通过反射去调用该方法
		// writeMethod.invoke(obj, args);
		// 获取对应属性的get方法
		Method readMethod = pd.getReadMethod();
		System.out.println(type);

		// 操作所有属性
		BeanInfo bi = Introspector.getBeanInfo(clazz);
		PropertyDescriptor[] pds = bi.getPropertyDescriptors();
		for (PropertyDescriptor p : pds) {

		}
	}

}
