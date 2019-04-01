package com.kkb.spring.bean.converter;

public interface TypeConverter {

	Object convert(String source);

	boolean handleType(Class<?> type);
}
