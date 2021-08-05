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
	<link rel="stylesheet" type="text/css" href="${staticUrl}/css/impression.css">
	<link rel="stylesheet" type="text/css" href="${staticUrl}/css/jquery.notesforlightbox.css">
</head>
<body>
	<jsp:include page="../views/include/head.jsp"></jsp:include>
	<!-- 广告 -->
	<div class="banner">
		<c:forEach var="activity" items="${activityList}">
		<a href="${activity.url}" title="${activity.title}"><img src="${activity.image}" alt=""></a>
		</c:forEach>
	</div>
	<!-- 分类栏 -->
	<div class="classify">
		<ul>
			<li><a title="0" href="javascript:queryCardList(0,null,1)">全部</a></li>
			<li><a title="1" href="javascript:queryCardList(1,null,1)">校园建筑</a></li>
			<li><a title="2" href="javascript:queryCardList(2,null,1)">花草树木</a></li>
			<li><a title="3" href="javascript:queryCardList(3,null,1)">借物抒情</a></li>
			<li><a title="4" href="javascript:queryCardList(4,null,1)">他&她</a></li>
			<li><a title="5" href="javascript:queryCardList(5,null,1)">其它</a></li>
		</ul>
		<!-- 搜索 -->
		<div class="search fr">
			<input id="keywords" type="text" name="keywords">
			<a href="javascript:void(0)" id="search"></a>
		</div>
	</div>
	<!-- 卡片摄影内容 -->
	<div class="card">
		<c:forEach var="card" items="${cardList}">
		<div class="card-con fl">
			<a href="${card.images}" title="${card.description}"><img src="${card.images}" alt="${card.title}"></a>
			<div class="card-pic-title">
				<p>${card.title}</p>
				<p class="card-pic-user" name="${card.id}"><a class="username" href="/user/${card.userId}.html">${card.userId}</a></p>
				<img class="xin" class="fr" src="${staticUrl}/image/xin.png"/>
				<span class="xin_nums">${card.stars}</span>
			</div>
		</div>
		</c:forEach>
		<!-- 卡片分页管理 -->
		<div class="card-page">
			<a id="prev-page" class="page-pre" href="javascript:queryCardList(getType(),getKeywords(),getPage()-1)">上一页</a>
			第 <span id="page" >${currentPage}</span> 页
			<a id="next-page" class="page-pre" href="javascript:queryCardList(getType(),getKeywords(),Number(getPage())+1)">下一页</a>
			共  <span id="pages">${pages}</span> 页
			跳转 <input id="toPage" type="text"><a href="javascript:queryCardList(getType(),getKeywords(),getToPage())"> 确认 </a>
		</div>
	</div>
	<jsp:include page="../views/include/foot.jsp"></jsp:include>
	<script type="text/javascript" src="${staticUrl}/js/jquery.notesforlightbox.js"></script>
	<script type="text/javascript" src="${staticUrl}/js/impression_card.js"></script>
	<script type="text/javascript">
		getUsername();
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
		// 获取查询条件
		function getKeywords(){
			return $("#keywords").attr("value");
		}
		// 设置查询类型
		var type = 0;// 查询类型
		function setType(value){
			type = value;
			return type;
		}
		// 获取查询类型
		function getType(){
			return type;
		}
		// 根据条件查询card
		function queryCardList(type, title, page){
			var pages = getPages();
			if(page < 1 || page > pages){
				$("#toPage").attr("value","");
				return;
			}
			$.ajax({
				url:"http://manage.icampus.com/rest/api/card",
				type: "get",
				dataType: "jsonp",
				data: {type:type, title:title, page:page},
				statusCode:{
					// 部分更新页面的card信息
					 200 : function(data){
						 var cardList = data.rows;
						 $(".card-con").remove();
						 for(var i in cardList){// 遍历json数组
							 $(".card").append(
								"<div class=\"card-con fl\">"+
									"<a href=\""+ cardList[i].images +"\" title=\"" + cardList[i].description + "\"><img src=\""+ cardList[i].images +"\" alt=\""+ cardList[i].title +"\"></a>" + 
									"<div class=\"card-pic-title\">" + 
									"<p>"+ cardList[i].title +"</p>" + 
									"<p class=\"card-pic-user\" name=\""+ cardList[i].id +"\"><a class=\"username\" href=\"/user/"+ cardList[i].userId +"\">"+ cardList[i].userId +"</a></p>" + 
									"<img class=\"xin\" class=\"fr\" src=\"${staticUrl}/image/xin.png\"/>" + 
									"<span id=\"xin_nums\">"+ cardList[i].stars +"</span>" +
									"</div>" + 
								"</div>");
						 }
						 // 获取用户名
						 getUsername();
						 // 给新添加的card添加事件
						 addEvent();
						 // 更新当前页
						 $("#page").html(data.currentPage);
						 // 更新总页数
						 $("#pages").html(data.total % 16 == 0? data.total / 16 : Math.ceil(data.total / 16));
						 if(getPages() == 0){
							 $("#pages").html(1);
						 }
					 },
					 500 : function(){
						 $.messager.alert('提示','查询失败!');
					 }
				   }
				});
			}
		// 搜索按钮绑定事件
		$(function(){
			$("#search").bind("click", function(){
				var title = $("#keywords").attr("value");
				queryCardList(0,title,1);
			});			
		});
		
		// 处理分类栏的部分
		$(function(){
			$(".classify li:first a").css({
				"background":"rgba(62,177,240,0.6)",
				"color":"#fff"
			});
			$(".classify li a").bind("click", function(){
				$(".classify li a").css({
					"background":"rgb(249,249,249)",
					"color":"#000"
				});
				var $this = $(this);
				$this.css({
					"background":"rgba(62,177,240,0.6)",
					"color":"#fff"
				});
				$("#keywords").attr("value","");// 每当点击分类栏时清空搜索框中的关键字
				var type = $(this).attr("title");// 每当点击分类栏时获取搜索类型
				setType(type);// 设置搜索类型
			});
		});
		// 初始化card的事件
		$(function(){
			addEvent();
		});
	</script>
</body>
</html>