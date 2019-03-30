<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客户添加</title>
</head>
<body>
	<form action="${pageContext.request.contextPath }/AddCustomerServlet" method="post">
		<table align="center" border="1">
			<tr>
				<td>客户名称：</td>
				<td><input type="text" name="custName" /></td>
				<td>负责人名称：</td>
				<td><input type="text" name="custUserName" /></td>
			</tr>
			
			<tr>
				<td colspan="4"><input type="submit" value="添加" /></td>
			</tr>
		</table>
	</form>
</body>
</html>