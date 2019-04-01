package com.kkb.spring.bean.resource;

import java.io.InputStream;

public class ClasspathResource implements Resource {

	private String path;

	public ClasspathResource(String path) {
		super();
		this.path = path;
	}

	@Override
	public InputStream getInputStream() {
		if (path == null || path.equals(""))
			return null;
		
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path);
		return inputStream;
	}

}
