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
	<link rel="stylesheet" type="text/css" href="${staticUrl}/back/css/admin.css">
</head>
<body>
	<!-- 导航菜单开始 -->
	<div class="nav">
		<div class="nav-con">
			<!-- logo -->
			<div class="nav-con-left fl">
				<a href="http:www.icampus.com">
					<img class="logo" src="${staticUrl}/image/icampus_logo2.png" alt="Icampus">
				</a>
			</div>
			<!-- 链接 -->
			<div class="nav-con-center">
				<ul class="nav-link">
					<li><a href="index.html">内容管理</a></li>
					<li><a href="impression.html"></a></li>
					<li><a href="souvenir.html"></a></li>
					<li><a href="message.html"></a></li>
					<li><a href="community.html"></a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="main">
		<div class="search">
			<form action="/rest/admin/query" method="get">
				&nbsp;标题：<input type="text" name="title">
				描述：<input type="text" name="description">
				<select name="cid" id="cid">
					<option value="1">首页广告</option>
					<option value="2">视频</option>
					<option value="3">声明</option>
					<option value="4">印象园活动</option>
				</select>
				<input type="submit" value="搜索"/>
			</form>
		</div>
		<div class="list">
			<table class="content-list">
				<tr>
					<th><input type="checkbox" value=""> 全选</th>
					<th>编号</th>
					<th>标题</th>
					<th>描述</th>
					<th>图片地址</th>
					<th>url</th>
					<th>内容类型</th>
					<th>创建时间</th>
					<th>更新时间</th>
				</tr>
				<c:forEach var="content" items="${contentList}"></c:forEach>
				<tr>
					<td><input type="checkbox" value="${content.id}"></td>
					<td>${content.id}</td>
					<td>${content.title}</td>
					<td>${content.description}</td>
					<td>${content.image}</td>
					<td>${content.url}</td>
					<td>${content.cid}</td>
					<td>${content.created}</td>
					<td>${content.updated}</td>
				</tr>
			</table>
			<div class="card-page fr">
				<a class="page-pre" href="/rest/admin/query?page=${currentPage - 1}">上一页</a>
				第<span> ${currentPage} </span>页
				<a class="page-pre" href="/rest/admin/query?page=${currentPage + 1}">下一页</a>
				共<span> ${pages} </span>页
				跳转 <input type="text"><a href="#"> 确认 </a>
		</div>
		</div>
	</div>
	<script type="text/javascript" src="${staticUrl}/js/jquery-1.8.2.min.js"></script>
	<script type="text/javascript">
		// 获取当前页数
		function getPage(){
			return $("#page").html();
		}
		// 获取跳转页面
		function getToPage(){
			return $("#toPage").attr("value");
		}
		// 获取总页数
		function getPages(){
			return $("#pages").html();
		}		
	</script>
</body>
</html>