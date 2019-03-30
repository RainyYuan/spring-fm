package com.kkb.spring.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.kkb.spring.po.Customer;

public class JdbcCustomerDaoImpl implements CustomerDao {

	@Override
	public Integer insert(Customer customer) {
		System.out.println("JdbcCustomerDaoImpl...");
		Integer line = null;
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			// 1. 注册驱动
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql:///kkb";
			String username = "root";
			String password = "root";
			// 2. 获取链接
			connection = DriverManager.getConnection(url, username, password);
			// 3. 获取预编译对象
			// preparedStatement对象要预编译的SQL语句，可以使用?占位符
			String sql = "INSERT INTO customer (cust_name ,cust_user_name) VALUES (?,?)";
			statement = connection.prepareStatement(sql);
			// 设置参数，替换?占位符
			statement.setString(1, customer.getCustName());
			statement.setString(2, customer.getCustUserName());
			// 4. 执行SQL语句
			line = statement.executeUpdate();
			// 5. 处理结果集
			// 返回值表示影响了几行
			System.out.println(line);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 6. 释放资源
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return line;

	}
}
