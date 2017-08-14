<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
function check11(){
	if(document.getElementById("itcode").value.length==0){
		//alert("用户名不能为空");
		document.getElementById("afteritcode").innerText="请输入itcode";
		return false;
	}else{
		document.getElementById("afteritcode").innerText="";
		return true;
	}
}
function check12(){
	if(document.getElementById("username").value.length==0){
		//alert("用户名不能为空");
		document.getElementById("afterusername").innerText="请输入用户名";
		return false;
	}else{
		document.getElementById("afterusername").innerText="";
		return true;
	}
}
function check13(){
	if(document.getElementById("amount").value.length==0){
		//alert("用户名不能为空");
		document.getElementById("afteramount").innerText="请输入金额";
		return false;
	}else{
		document.getElementById("afteramount").innerText="";
		return true;
	}
}

</script>
</head>
<body>
在这里添加充值信息
${msg }
<form name="form1" action="balance_adding">
<table>
<tr><td>ITCode</td><td><input id="itcode" name="itcode" onblur="check11()"></td><td><span id="afteritcode" style="color:red"></span></td></tr>
<tr><td>姓名:</td><td><input id="username" name="username" onblur="check12()"></td><td><span id="afterusername" style="color:red"></span></td></tr>
<tr><td>充值金额:</td><td><input name="amount" id="amount"  onblur="check13()"></td><td><span id="afteramount" style="color:red"></span></td></tr>
<tr><td><input type="submit" value="充值"></td><td></td></tr>
</table>
</form>


</body>
</html>