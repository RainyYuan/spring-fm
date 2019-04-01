package com.kkb.spring.bean.converter;

public class IntegerTypeConverter implements TypeConverter {

	@Override
	public Object convert(String source) {
		return Integer.parseInt(source);
	}

	@Override
	public boolean handleType(Class<?> type) {
		// type是否是Integer的父类或者当前类
		return type.isAssignableFrom(Integer.class);
	}

}
