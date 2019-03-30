package com.kkb.spring.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0Utils {

	private C3P0Utils() {
	}

	private static DataSource dataSource = new ComboPooledDataSource();

	// 线程的局部变量，保证同一线程内的代码可以使用同一Connection对象，比如Service层和dao层的代码
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

	/**
	 * 获取连接池
	 * 
	 * @return
	 */
	public static DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * 获取连接
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取线程安全和线程内单例的的连接对象
	 * 
	 * @return
	 */
	public static Connection getCurrentConnection() {
		Connection connection = threadLocal.get();
		if (connection == null) {
			connection = getConnection();
			threadLocal.set(connection);
		}
		return connection;
	}

	public static void main(String[] args) throws Exception {
		// Connection connection = getConnection();
		// connection.setAutoCommit(false);

		Connection connection = getCurrentConnection();
		connection.setAutoCommit(false);
	}
}
