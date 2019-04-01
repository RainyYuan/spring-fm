package com.kkb.spring.bean.reader;

import java.io.InputStream;

import org.dom4j.Document;

public interface DocumentReader {

	/***
	 * 使用Sax解析XML文件，创建Document对象
	 * @param inputStream
	 * @return
	 */
	Document loadDocument(InputStream inputStream);
}
