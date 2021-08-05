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
	<title>Insert title here</title>
	<link rel="stylesheet" type="text/css" href="${staticUrl}/css/foot.css">
</head>
<body>
	<!-- 网站尾部 -->
	<div class="foot">
		<div class="one-word">
			<c:forEach var="statement" items="${statementList}">
			<a href="${statement.url}" target="_blank">${statement.description}</a>
			</c:forEach>
		</div>
		<ul class="">
			<li><a href="#">商务合作</a></li>
			<li><a href="#">加入我们</a></li>
			<li><a href="#">友情链接</a></li>
			<li><a href="#">常见问题</a></li>
			<li><a href="#">意见反馈</a></li>
		</ul>
		<span class="copyright">张家宝版权所有 © 2017-201？   粤ICP备33301212号</span>
	</div>
</body>
</html>