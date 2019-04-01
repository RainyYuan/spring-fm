package com.kkb.spring.bean.factory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kkb.spring.bean.converter.IntegerTypeConverter;
import com.kkb.spring.bean.converter.TypeConverter;
import com.kkb.spring.bean.definition.BeanDefinition;
import com.kkb.spring.bean.definition.PropertyValue;
import com.kkb.spring.bean.definition.RuntimeBeanReference;
import com.kkb.spring.bean.definition.TypeStringValue;
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

	private List<TypeConverter> converters = new ArrayList<>();
	
	public DefaultListableBeanFactory(String path) {
		loadBeanDefinitions(path);
		initConverters();
	}

	private void initConverters() {
		converters.add(new IntegerTypeConverter());
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

	private Map<String, Object> singleBeanInstances = new HashMap<>();

	@Override
	public Object getBean(String beanName) {
		// 1、根据beanName去对象缓存（Map数据结构<String,Object>）中获取对应的Bean，如果没回去成功，再走下面的流程
		Object instanceObj = singleBeanInstances.get(beanName);
		if (instanceObj != null) {
			return instanceObj;
		}
		// 2、根据beanName去beanDefinitions集合中，获取BeanDefinition对象
		BeanDefinition beanDefinition = beanDefinitions.get(beanName);
		// a)创建对象.从BeanDefinition中获取beanClassName，根据该beanClassName可以反射创建
		instanceObj = createBean(beanDefinition);
		if (instanceObj == null) {
			return null;
		}
		// b)填充属性.从BeanDefinition中获取PropertyValue集合，解析该集合中不同类型的属性值完成属性填充
		setPropertyValue(beanDefinition, instanceObj);
		// TypeStringValue -----八种基本类型+String类型
		// value
		// targetType 需要单独获取（反射、内省）
		//
		// 通过反射或者内省进行属性填充
		// RuntimeBeanReference-----引用类型（自定义的POJO类型）
		// refName-----getBean()
		//
		// 通过反射或者内省进行属性填充
		// c)对象初始化.从BeanDefinition中获取initMethod信息，根据反射调用指定的method方法
		initBeanInstance(beanDefinition, instanceObj);

		return instanceObj;
	}

	/***
	 * 对象初始化操作
	 * 
	 * @param beanDefinition
	 * @param instanceObj
	 */
	private void initBeanInstance(BeanDefinition beanDefinition, Object instanceObj) {
		try {
			String initMethod = beanDefinition.getInitMethod();
			if (initMethod == null || initMethod.equals("")) {
				return;
			}

			// 通过反射去调用初始化方法
			Class<?> clazz = instanceObj.getClass();
			Method method = clazz.getDeclaredMethod(initMethod);
			method.invoke(instanceObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * 填充属性
	 * 
	 * @param beanDefinition
	 * @param instanceObj
	 */
	private void setPropertyValue(BeanDefinition beanDefinition, Object instanceObj) {
		try {
			List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();

			String beanClassName = beanDefinition.getBeanClassName();
			Class<?> clazz = Class.forName(beanClassName);
			for (PropertyValue propertyValue : propertyValues) {
				// 获取属性名称
				String name = propertyValue.getName();
				// 获取属性值（未经过处理的值，比如TypeStringValue和RuntimeBeanReference）
				Object value = propertyValue.getValue();

				// 可以直接赋值使用的值
				Object valueToApply = null;
				if (value instanceof RuntimeBeanReference) {
					String refName = ((RuntimeBeanReference) value).getRefName();
					valueToApply = getBean(refName);
				} else if (value instanceof TypeStringValue) {
					TypeStringValue typeStringValue = (TypeStringValue) value;
					valueToApply = resolveValue(name, typeStringValue,clazz);

				} else {
					valueToApply = value;
				}

				// 设置对应的属性
				setField(instanceObj, clazz, name, valueToApply);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void setField(Object instanceObj, Class<?> clazz, String name, Object valueToApply)
			throws NoSuchFieldException, IllegalAccessException {
		// 获取指定名称的属性
		Field field = clazz.getDeclaredField(name);
		// 暴力破解
		field.setAccessible(true);
		// 属性赋值
		field.set(instanceObj, valueToApply);
	}

	/***
	 * 处理TypeStringValue的值
	 * 
	 * @param typeStringValue
	 * @param clazz 
	 * @return
	 */
	private Object resolveValue(String name, TypeStringValue typeStringValue, Class<?> clazz) {
		Object value = null;
		try {
			// 获取指定的属性名称
			Field field = clazz.getDeclaredField(name);
			Class<?> type = field.getType();
			// String类型不需要转换
			if (type.isAssignableFrom(String.class)) {
				return typeStringValue.getValue();
			}
			//进行类型转换
			for (TypeConverter typeConverter : converters) {
				if(typeConverter.handleType(type)) {
					value = typeConverter.convert(typeStringValue.getValue());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/***
	 * 创建bean实例，只是new了个对象，属性此时是没有值的
	 * 
	 * @param beanDefinition
	 * @return
	 */
	private Object createBean(BeanDefinition beanDefinition) {
		Object instance = null;
		try {
			String beanClassName = beanDefinition.getBeanClassName();
			Class<?> clazz = Class.forName(beanClassName);

			instance = clazz.newInstance();
			// 可以通过指定的构造参数，去调用对应的构造方法去创建对象，默认调用无参构造
			// Constructor<?> constructor = clazz.getConstructor();
			// Object instance2 = constructor.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}

}
