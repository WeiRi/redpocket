<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
欢迎来到钱的世界,<a href="balance_add">充值</a>
<hr>
您可以查看某天您的交易记录：<hr>
<form name="from1" action="trade_check">
<table>
<tr><td>输入itcode</td><td><input name="itcode"></td></tr>
<tr><td>输入用户姓名</td><td><input name="username"></td></tr>
<tr><td>输入日期</td><td><input name="date"></td></tr>
<tr><td><input type="submit" value="提交"></td></tr>
</table>
</form>
<hr>
开启红包雨<a href="luckyadmin">进入红包雨界面</a><hr>
<a href="luck">进入抢红包界面</a><hr>
<a href="perform">进入打赏界面</a><hr>
<a href="programlist">查看节目打赏名次</a>
现在服务器的时间是<c:out value="${serverTime }"></c:out>


</body>
</html>