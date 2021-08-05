<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String staticUrl = "http://static.icampus.com";
	request.setAttribute("staticUrl", staticUrl);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" type="text/css" href="${staticUrl}/css/head.css">
</head>
<body>
	<!-- 导航菜单开始 -->
	<div class="nav">
		<div class="nav-con">
			<!-- logo -->
			<div class="nav-con-left fl">
				<a href="http://www.icampus.com">
					<img class="logo" src="${staticUrl}/image/icampus_logo2.png" alt="Icampus">
				</a>
			</div>
			<!-- 链接 -->
			<div class="nav-con-center">
				<ul class="nav-link">
					<li><a href="http://www.icampus.com">首页</a></li>
					<li><a href="http://www.icampus.com/impression.html">印象园</a></li>
					<li><a href="http://www.icampus.com/souvenir.html">物流连</a></li>
					<li><a href="http://www.icampus.com/message.html">简信</a></li>
					<li><a href="http://www.icampus.com/community.html">社区</a></li>
				</ul>
			</div>
			<div class="nav-con-right fr">
				<a href="http://sso.icampus.com/user/login.html">登录</a>
				<a href="http://sso.icampus.com/user/register.html">注册</a>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${staticUrl}/js/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="${staticUrl}/js/jquery.cookie.js"></script>
	<script type="text/javascript" src="${staticUrl}/js/icampus_login.js"></script>
	<script type="text/javascript">
		/* $(function(){
			$("#portrait-link").hover(
				function(data){
					$("#user-details").css("visibility","none");
					alert("dsd");
				},
				function(data){
					$("#user-details").css("visibility","hidden");
					alert("dsd");
				});
			
			$("#user-details").hover(
				function(data){
					$("#user-details").css("visibility","none");
					alert("dsd");
				},
				function(data){
					$("#user-details").css("visibility","hidden");
					alert("dsd");
				});
		}) */
	</script>
</body>
</html>