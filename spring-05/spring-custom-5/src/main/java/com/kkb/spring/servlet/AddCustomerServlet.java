package com.kkb.spring.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.kkb.spring.po.Customer;
import com.kkb.spring.service.CustomerService;
import com.kkb.spring.service.CustomerServiceImpl;

public class AddCustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// 调用业务层（面向接口开发）
	// private CustomerService service = BeanFactory.getCustomerService();
	// private CustomerService service = (CustomerService)
	// BeanFactory.getBean("customerService");

	// 这种实现是将Servlet和Service实现类强耦合在一起了，不利于扩展（开闭原则）
	private CustomerService service = new CustomerServiceImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		try {
			// 接收参数
			Map<String, String[]> parameterMap = request.getParameterMap();

			// 封装数据
			Customer customer = new Customer();
			BeanUtils.populate(customer, parameterMap);

			// 第二步：调用业务方法
			service.addCustomer(customer);

			// 响应
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print("添加成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
