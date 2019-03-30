package com.kkb.spring.service;

import com.kkb.spring.dao.CustomerDao;
import com.kkb.spring.dao.DbUtilsCustomerDaoImpl;
import com.kkb.spring.po.Customer;

public class CustomerServiceImpl implements CustomerService {

	// 这种实现是将Service和dao实现类强耦合在一起了，不利于扩展（开闭原则）
	// 问题1：强耦合（dao和service实现类）
	// 问题2：开闭原则--对修改关闭、对扩展开放
	// 问题3：如果修改到的实现类。则需要修改代码
	// 如何解决该问题
	// 解决方案：首先要面向接口编程(只关心接口，不需要关心实现类)
	// 解决方案：通过工厂设计模式来解决强耦合和开闭原则的问题
	private CustomerDao customerDao = new DbUtilsCustomerDaoImpl();
	// private CustomerDao customerDao = new JdbcCustomerDaoImpl();
	// private CustomerDao customerDao = BeanFactory.getCustomerDao();
	// private CustomerDao customerDao ;

	// 依赖注入的演示
	/*
	 * private CustomerDao customerDao;
	 * 
	 * public void setCustomerDao(CustomerDao customerDao) { this.customerDao =
	 * customerDao; }
	 */

	public CustomerServiceImpl() {
		System.out.println("CustomerServiceImpl被构造了");
	}

	@Override
	public String addCustomer(Customer customer) {

		// 开启事务（如何开启？想想如何开启JDBC的事务）
		// connection.setAutoCommit(false);

		// ThreadLocal放入Connection连接
		// ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
		// threadLocal.set(value);

		Integer line = customerDao.insert(customer);
		if (line != null && line > 0) {
			return "添加成功！";
		} else {
			return "添加失败！";
		}

		// 事务或者回滚事务
		// connection.commit
	}

}
