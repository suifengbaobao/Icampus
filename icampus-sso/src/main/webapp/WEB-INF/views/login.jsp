<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>      
<%
	String staticUrl = "http://static.icampus.com";
	request.setAttribute("staticUrl", staticUrl);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Icampus爱校园</title>
	<link rel="stylesheet" type="text/css" href="${staticUrl}/css/head.css">
	<link rel="stylesheet" type="text/css" href="${staticUrl}/css/login.css">
</head>
<body>
	<jsp:include page="../views/include/head.jsp"></jsp:include>
	<div id="container">
		<div id="img1"></div>
        <form action="javascript:void(0)" class="loginform" id="login" method="post">
            用户名：<br>
            <input id="username" name="username" type="text" style="width: 250px; height: 28px;"><br><br>
            密码：<br>
            <input id="password" name="password" type="password"  style="width: 250px; height: 28px;">
            <br><br>
            <span style="color:red;" id="error"><br></span>
            <input type="submit" value="现在登录" >
        </form>
        <a href="/user/register.html">免费注册>></a>
    </div>
    <script type="text/javascript">
    $("#username").children("input").focus(function () {
		$("#error").html("");
	});
    $("#password").children("input").focus(function () {
    	$("#error").html("");
	});
    $(function(){
		$(".loginform").submit(function(){
			var username = $("#username").attr("value");
			var password = $("#password").attr("value");
			$.ajax({
				url: 'http://sso.icampus.com/service/user/doLogin',
				type: 'post',
				data:{username:username,password:password},
				dataType: 'json',
				statusCode:{
					200:function(data){
						if(data.status == 200){
							window.location.href="http://www.icampus.com";
						}else{
							$("#error").html("用户名或密码错误！");
							return false;
						}
					}
				}
			});
		});
	});	
    </script>
</body>
</html>