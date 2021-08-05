<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>      
<%
	String staticUrl = "http://static.icampus.com";
	request.setAttribute("staticUrl", staticUrl);
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Icampus爱校园</title>
	<link rel="stylesheet" type="text/css" href="${staticUrl}/css/souvenir.css">
</head>
<body>
	<jsp:include page="../views/include/head.jsp"></jsp:include>
	<!-- 纪念品 -->
	<div class="souvenir">
		<!-- 纪念品内容 -->
		<c:forEach var="item" items="${itemList}">
		<div class="souvenir-con">
			<div class="souv-title fl">${item.title}</div>
			<div class="souv-pic fl">
				<a href="${item.image}" class="jqzoom" title="${item.title}">
                     <img src="${item.image}" alt=""/>
                </a>
			</div>
			<div class="souv-descrip fl">
				<a style="color:#666;" href="javascript:void(0)">${item.description}</a>
			</div>
			<span class="souv-price">${item.price/100.0} ￥</span>
			<a id="${item.id}" class="souv-star" href="javascript:void(0)">关注</a>
		</div>
		</c:forEach>
	</div>
	<jsp:include page="../views/include/foot.jsp"></jsp:include>
	<script type="text/javascript" src="${staticUrl}/js/jquery.jqzoom.js"></script>
	<script type="text/javascript" src="${staticUrl}/js/alertWin.js"></script>
	<script type="text/javascript">
		// 图片放大镜功能
		$(function () {
                $(".jqzoom").jqzoom({ //绑定图片放大插件jqzoom
                    zoomWidth: 200, //小图片所选区域的宽
                    zoomHeight: 200, //小图片所选区域的高
                    zoomType: 'reverse' //设置放大镜的类型
                });
            });
		// 关注按钮请求时间，添加到用户购物车中
		$(function(){
			$(".souv-star").bind("click",function(){
				var $portrait = $("#portrait");
				console.log(typeof($portrait.attr("src")));
				if(typeof($portrait.attr("src")) != 'string'){
					window.location.href="http://sso.icampus.com/user/login.html";
					return false;
				}
				var $this = $(this);
				var souvId = $this.attr("id");
				$.ajax({
					url: 'http://www.icampus.com/service/cart/save/' + souvId,
					type: 'get',
					dataType: 'json',
					statusCode:{
						200:function(){
							jQuery.alertWindow("关注成功");
						}
					},
				})
			});
		});
	</script>
</body>
</html>