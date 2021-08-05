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
	<link rel="stylesheet" type="text/css" href="${staticUrl}/css/message.css">
</head>
<body>
	<jsp:include page="../views/include/head.jsp"></jsp:include>
	<!-- 简信 -->
	<div class="message clearfix">
		<div class="hot-message fl">
			<div class="hot-message-nav">
	    		<span>精 选</span> 简 信：
	    	</div>
	    	<!-- 一条简信 -->
	    	<c:forEach var="message" items="${sMessageList}">
			<div class="hot-message-con">
				<a class="user-pic fl" href="#"><img class="fl" src="${staticUrl}/image/default_user.jpg" alt=""></a>
				<div class="hot-message-content">
					<a href="/user/${message.userId}.html"><span class="username" id="${message.userId}"></span>：</a>
					${message.content}
					<br><span class="time">${message.created}</span>
					<a class="dianzan" href="javascript:void(0)"></a><span id="${message.id}" class="message-star-nums">(${message.stars})</span>
				</div>
			</div>
			</c:forEach>
		</div>
		<!-- 写简信 -->
		<div class="new-message fr">
			<div class="send-message-nav">写一封简信</div>
			<div class="send-message">
				<form id="sendMessage" action="/service/message/save" method="post">
				<textarea name="content" id="message" cols="30" rows="10"></textarea>
				<input id="statue" name="state" type="radio" value="1"> <span>邮寄给自己</span>
				<input id="send" type="submit" value="寄出">
				</form>
			</div>
			<!-- 最新简信 -->
			<div class="new-message-nav">
	    		<span>最 新</span> 简 信：
	    	</div>
	    	<!-- 一条新简信 -->
	    	<c:forEach var="message" items="${cMessageList}">
	    	<div class="new-message-con">
				<a class="user-pic fl" href="#"><img class="fl" src="${staticUrl}/image/default_user.jpg" alt=""></a>
				<div class="new-message-content">
					<a href="/user/${message.userId}.html"><span class="username" id="${message.userId}"></span>：</a>
					${message.content}
					<br><span class="time">${message.created}</span>
					<a class="dianzan" href="javascript:void(0)"></a><span id="${message.id}" class="message-star-nums">(${message.stars})</span>
				</div>
			</div>
			</c:forEach>
		</div>
	</div>
	<jsp:include page="../views/include/foot.jsp"></jsp:include>
	<script type="text/javascript" src="${staticUrl}/js/time_format.js"></script>
	<script type="text/javascript">
		// 格式化时间
		$(function(){
			$(".time").each(function(i,ele){
				var $this = $(this);
				var time = $this.html();
				var date = new Date(Number(time));
				$this.html(date.pattern("yyyy年MM月dd日 hh:mm:ss"));
			});
		});
		// 当点赞后，更新点赞数目
		$(function(){
			$(".dianzan").bind("click",function(){
				var $this = $(this);
				var message_id = $this.next().attr("id");
				$.ajax({
					url: 'http://manage.icampus.com/rest/api/message/star/' + message_id,
					type: 'get',
					dataType: 'jsonp',
					statusCode:{
						200:function(data){
							$this.next(".message-star-nums").html("("+ data.stars +")");
						}		
					}
				})
			});
		});
		// 获取用户头像
		$(function(){
			$(".username").each(function(index, el) {
				var $this = $(this);
				var user_id = $(this).attr("id");
				$.ajax({
					url: 'http://manage.icampus.com/rest/api/user/' + user_id,
					type: 'get',
					dataType: 'jsonp',
					statusCode:{
						200:function(data){
							$this.html(data.username);
							$this.parents("div").prev("a").children("img").attr("src",data.image);
						}
					}
				});
			});
		});
		// 绑定寄出按钮实践
		$("#sendMessage").submit(function(data){
			var content = $("#message").attr("value");
			if(content.length > 140){
				alert("输入的简信过长！");
				return false;
			}
			if(content.length == 0){
				alert("输入的简信不能为空！");
				return false;
			}
		})
	</script>
</body>
</html>