package com.kkb.spring.bean.resource;

import java.io.InputStream;

public interface Resource {
	
	/**
	 * 获取不同资源位置下的配置文件信息
	 * @return
	 */
	InputStream getInputStream();
}
