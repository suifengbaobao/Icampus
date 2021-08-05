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
<title></title>
<link rel="stylesheet" type="text/css" href="${staticUrl}/css/send-pic.css">
</head>
<body>
	<form class="registerform" action="javascript:void(0)" method="post">
		<div class="send-left fl">
			<div class="select-pic">
				<div class="pic"><img id="send-pic" class="send-pic" src=""></div>
				<input id="image_url" type="hidden" name="images" value=""> <a href=""
					class="a-upload"><input id="uploadFile" type="file" name="uploadFile" onchange="showPreview(this)">选择图片</a> <a
					id="file_upload" href="javascript:void(0)">上传</a>
			</div>
		</div>
		<div class="send-right fr">
			<div class="div-title">
				标题：<input type="text" name="title"> <select id="type" name="type"
					id="type">
					<option value="0">选择类型</option>
					<option value="1">校园建筑</option>
					<option value="2">花草树木</option>
					<option value="3">借物抒情</option>
					<option value="4">他&她</option>
					<option value="5">其它</option>
				</select>
			</div>
			<div class="div-description">
				描述：<textarea name="description" value=""></textarea>
			</div>
			<div class="div-site">
				地点：<input type="text" name="site">
			</div>
			<input id="submit" type="submit" value="分享">
		</div>
	</form>
	<script type="text/javascript" src="${staticUrl}/js/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="${staticUrl}/js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${staticUrl}/js/alertWin.js"></script>
	<script type="text/javascript">
		// 上传图片显示
		var uploadFile = null;
	    function showPreview(source) {
	        var file = source.files[0];
	        uploadFile = file;
	        if (window.FileReader) {
	            var fr = new FileReader();
	            fr.onloadend = function(e) {
	                document.getElementById("send-pic").src = e.target.result;
	            };
	            fr.readAsDataURL(file);
	        }
	    }
	    // 上传文件
		$(function () {
            $("#file_upload").click(function () {
                ajaxFileUpload();
            })
        })
		function ajaxFileUpload() {
            $.ajaxFileUpload
            (
                {
                    url: '/service/pic/upload', //用于文件上传的服务器端请求地址
                    type:'post',
                    secureuri: false, //是否需要安全协议，一般设置为false
                    fileElementId: 'uploadFile', //文件上传域的ID
                    dataType: 'json', //返回值类型 一般设置为json
                    success:function(data){
                    	$("#image_url").attr("value",data.url);
                    	console.log($("#image_url").attr("value"));
                    	jQuery.alertWindow("上传成功");
                    }  
                }
            )
            return false;
        }
        // 表单提交请求
        $(function(){
			$(".registerform").submit(function(){
				alert("");
				var images = $("#image_url").attr("value");
				var title = $(".div-title").children("input").attr("value");
				var type = $("#type").attr("value");
				var description = $(".div-description").children("textarea").val();
				var site = $(".div-site").children("input").attr("value");
				var params = {
					"images":images,
					"title":title,
					"type":type,
					"description":description,
					"site":site
				}
				$.ajax({
					url: '/service/person/card/save',
					type: 'post',
					data:params,
					dataType: 'json',
					statusCode:{
						201:function(data){
							jQuery.alertWindow("分享成功");
							return false;
						},
						500:function(){
							jQuery.alertWindow("分享失败");
							return false;
						}
					}
				});
			});
		});
	</script>
</body>
</html>