<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    String staticUrl = "http://static.icampus.com";
    request.setAttribute("staticUrl", staticUrl);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人中心</title>
<link rel="stylesheet" type="text/css" href="${staticUrl}/css/person-index.css">
</head>
<body>
	<div class="main-page-right fl">
	<c:forEach var="card" items="${cardList}">
		<div class="card-con fl">
			<a href="${card.images}" title="${card.description}"><img
				src="${card.images}" alt="${card.title}"></a>
			<div class="card-pic-title">
				<p>${card.title}</p>
				<p class="card-pic-user" name="${card.id}">
					<a class="card-delete-link"
						href="javascript:deleteCard(${card.id})">删除</a>
				</p>
				<img class="xin" class="fr" src="../image/xin.png" /> <span
					id="xin_nums">${card.stars}</span>
			</div>
		</div>
	</c:forEach>
	</div>
	<!-- 卡片分页管理 -->
	<div class="card-page fr" id="${pages}">
		<a id="1" class="next-page"
			href="javascript:queryCardList(${user.id},Number(getPage())+1)">加载更多</a>
	</div>
	<script type="text/javascript" src="${staticUrl}/js/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="${staticUrl}/js/alertWin.js"></script>
	<script type="text/javascript" src="${staticUrl}/js/jquery.notesforlightbox.js"></script>
	<script type="text/javascript" src="${staticUrl}/js/person_card.js"></script>
	<script type="text/javascript">
			
		// 绑定发图按钮事件，切换视图
		$(function(){
			$("#send-pic").bind("click",function(){
				$(".main-page-right").attr("src","send-pic.jsp");
			})
			$("#person-index").bind("click",function(){
				$(".main-page-right").attr("src","person-index.jsp");
			})
		})
		$(function(){
			// 更换性别图标
			var sex = $("#username").attr("title");
			if(sex == "女"){
				$("#username").css("background","url(http://static.icampus.com/image/women.png) no-repeat right center");
			}
			// 绑定菜单键
			$(".main-menu a:first").css("color","#ff0000");
			$(".icon:first").css("border-left","10px solid #ff0000");
			// 是否出现页面管理
			if($(".card-page").attr("id") <= 1){
				$(".card-page").css("visibility","hidden");
			}else{
				$(".card-page").css("visibility","none");
			}
		});
		// 加入滑动效果
		$(function(){
			$(".main-menu a").hover(
				function(){
					$(this).css("color","#ff0000");
					$(this).children().css("border-left","10px solid #ff0000");
				},
				function(){
					$(this).css("color","#000");
					$(this).children().css("border-left","10px solid #aaa");
				});
		});
		// 给卡片添加事件
		$(function(){
			addEvent();
		})
		// 获取当前页数
		function getPage(){
			return $(".next-page").attr("id");
		}
		function queryCardList(userId, page){
				$.ajax({
					url:"http://manage.icampus.com/rest/api/card/" + userId + "/" + page,
					type: "get",
					dataType: "jsonp",
					statusCode:{
						// 部分更新页面的card信息
						 200 : function(data){
							 var cardList = data.rows;
							 for(var i in cardList){// 遍历json数组
								 var $selector = $(".card-con:last");
								 console.log($selector);
								 $selector.after(
									"<div class=\"card-con fl\">"+
										"<a href=\""+ cardList[i].images +"\" title=\"" + cardList[i].description + "\"><img src=\""+ cardList[i].images +"\" alt=\""+ cardList[i].title +"\"></a>" + 
										"<div class=\"card-pic-title\">" + 
										"<p>"+ cardList[i].title +"</p>" + 
										"<p class=\"card-pic-user\" name=\"" + cardList[i].id + "\"><a class=\"card-delete-link\" href=\"javascript:deleteCard(" +cardList[i].id + ") \">删除</a></p>" + 
										"<img class=\"xin\" class=\"fr\" src=\"${staticUrl}/image/xin.png\"/>" + 
										"<span id=\"xin_nums\">"+ cardList[i].stars +"</span>" +
										"</div>" + 
									"</div>");
							 }
							 // 给新添加的card添加事件
							 addEvent();
							 // 更新当前页
							 $(".next-page").attr("id",data.currentPage);
							 if($(".card-page").attr("id") <= data.currentPage){
								$(".card-page").css("visibility","hidden");
							 }else{
								$(".card-page").css("visibility","none");
							 }
						 },
						 500 : function(){
							 $.messager.alert('提示','查询失败!');
						 }
					   }
					});
			}
	</script>
</body>
</html>