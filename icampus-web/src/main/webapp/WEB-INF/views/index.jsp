<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>     
<%
	String staticUrl = "http://static.icampus.com";
	request.setAttribute("staticUrl", staticUrl);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Icampus爱校园</title>
	<link rel="stylesheet" type="text/css" href="${staticUrl}/css/index.css">
</head>
<body>
	<jsp:include page="../views/include/head.jsp"></jsp:include>
	<!-- 大广告 -->
	<div id="banner">
		<div id="banner-con">
		<c:forEach var="sad" items="${sAdList}">
        <a href="${sad.url}" title="${sad.title}" target="_blank"><img class="img" src="${sad.image}" alt="${sad.title}"></a>
        </c:forEach>
        <!--<img src="img/tm-img-06.jpg" alt="">-->
    	</div>
    	<ul id="tab">
	        <!--&lt;!&ndash;
	        <li class="selected">1</li>
	        <li>2</li>
	        <li>3</li>
	        <li>4</li>
	        -->
    	</ul>
    	<div id="arrows">
        	<span id="prev"></span>
        	<span id="next"></span>
    	</div>
	</div>
	<!-- 文章 -->
	<div class="article">
		<div class="art-title"><a href="/article.html">更多+</a></div>
		<div class="art-con">
			<!-- 一篇文章 -->
			<c:forEach var="article" items="${articleList}">
			<div class="art-content fl" id="${article.id}">
				<div class="art-content-top clearfix">
					<div class="art-con-user-pic fl">
						<a href="/user/${article.userId}.html" target="_blank"><img class="user-img" src="" id="${article.userId}"></a>
					</div>
					<div class="art-con-title fr">
						<p class=""><a href="/article/${article.id}.html" target="_blank">${article.title}</a></p>
					</div>
				</div>
				<div class="art-content-bottom">
					${fn:substring(article.description,0,80)}……
				</div>
			</div>
			</c:forEach>
		</div>
	</div>
	<!-- 简美短片 -->
	<div class="video">
		<div class="video-title"></div>
		<div class="video-con">
			<div class="video-content fl">
				<div id="begin"></div>
   				<video id="video" src="" controls style="width:700px; height:390px;"></video>
			</div>
			<div class="video-nav fr">
				<c:forEach var="video" items="${videoList}">
				<div class="video-nav-con">
					<a href="javascript:void(0);" title="${video.title}" value="${video.url}">
						<img src="${video.image}" alt="">
						<p class="v-nav-con-descrip">${fn:substring(video.description,0,32)}……</p>
					</a>
				</div>
				</c:forEach>
			</div>
		</div>
	</div>
	<jsp:include page="../views/include/foot.jsp"></jsp:include>
	<script src="${staticUrl}/js/icampus_banner.js" type="text/javascript"></script>
	<script src="${staticUrl}/js/jslib.js" type="text/javascript"></script>
	<script type="text/javascript">
		// 初始化视频信息
		$(function(){
			$(".video-nav-con a:first").css({"border":"1px solid #000","background":"#fff"});
			var videoUrl = $(".video-nav-con a:first").attr("value");
			$("#video").attr("src",videoUrl);
		});
		// 给视频导航栏添加事件
		$(function(){
			$(".video-nav-con a").bind("click", function(){
				var $this = $(this);
				$(".video-nav-con a").css("border","1px solid #ccc");
				$this.css({"border":"1px solid #000","background":"#fff"});
				var videoUrl = $this.attr("value");
				$("#video").attr("src",videoUrl);
			});
		});
		// 获取用户头像
		$(function(){
			$(".user-img").each(function(index, el) {
				var $this = $(this);
				var user_id = $(this).attr("id");
				$.ajax({
					url: 'http://manage.icampus.com/rest/api/user/' + user_id,
					type: 'get',
					dataType: 'jsonp',
					statusCode:{
						200:function(data){
							$this.attr("src",data.image);
						}
					}
				});
			});
		});
		//视频点击按钮时间
		var oContainer = document.getElementsByClassName('video-content');
	    var oBegin = document.getElementById('begin');
	    var video = document.querySelector("video");
	    var Flag = false;//true为播放 false为暂停
	
	    oContainer.onclick = function () {
	        var timer;
	        Flag = !Flag;
	        if(Flag){
	            video.play();
	            oBegin.style.backgroundPositionX = '-335px';
	            timer = setTimeout("oBegin.style.display = 'none';",800)
	        }
	        else{
	            video.pause();
	            oBegin.style.backgroundPositionX = '-172.5px';
	            oBegin.style.display = 'block';
	            clearTimeout(timer);
	        }
	    };
	</script>
</body>
</html>