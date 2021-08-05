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
	<link rel="stylesheet" type="text/css" href="${staticUrl}/css/register.css">
</head>
<body>
	<jsp:include page="../views/include/head.jsp"></jsp:include>
	<!-- 注册表单 -->
	<div class="register">
		<form class="registerform" action=javascript:void(0)" method="post">
			<div class="register-left fl">
				<div class="div-username">
					&nbsp;&nbsp;* 用户名：<input type="text" name="username"><span style="color:#ff0000;font-size:13px;" class="error"></span>
				</div>
				<div class="div-password">
					* 请设置密码：<input type="password" name="password"><span style="color:#ff0000;font-size:13px;" class="error"></span>
				</div>
				<div class="div-repassword">
					* 请确认密码：<input type="password" name="password2"><span style="color:#ff0000;font-size:13px;" class="error"></span>
				</div>
				<div class="div-sex">
					&nbsp;&nbsp;&nbsp;* 性别：<input checked="" type="radio" name="sex" value="男">男
					<input checked="" type="radio" name="sex" value="女">女
					&nbsp;<span style="color:#ff0000;font-size:13px;" class="error"></span>
				</div>
				<div class="div-birthday">
					&nbsp;* 出生日期：<input type="date" name="birthday">
				</div>
				<div class="div-email">
					&nbsp;&nbsp;&nbsp;* 邮箱：<input type="email" name="email" maxlength="40"><span style="color:#ff0000;font-size:13px;" class="error"></span>
				</div>
				<div class="div-school">
					&nbsp;&nbsp;&nbsp;* 学校：<input type="text" name="school" maxlength="50"><span style="color:#ff0000;font-size:13px;" class="error"></span>
				</div>
			</div>
			<div class="register-right fr">
				<div class="div-user-pic">
					<img id="user-pic" class="user-pic" src="http://static.icampus.com/image/default_user_pic2.jpg">
					<span class="a-upload"><input id="uploadFile" type="file" name="uploadFile" value="sss" onchange="showPreview(this)"/>选择头像</span>
					<a id="file_upload" href="javascript:void(0)">上传</a>
					<input id="image_url" name="image" type="hidden" value=""></input>
				</div>
				<div class="div-hobby">
					爱好：<input type="text" name="hobby" maxlength="50">
				</div>
				<div class="div-signature">
					签名：<textarea name="signature" cols="30" rows="10"></textarea>
				</div>
				<div class="div-submit">
					<input type="submit" value="注册"><br><span style="color:#ff0000;font-size:13px;" class="register-error"></span>
				</div>
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript" src="${staticUrl}/js/register_validate.js"></script>
	<script type="text/javascript" src="${staticUrl}/js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${staticUrl}/js/alertWin.js"></script>
	<script type="text/javascript">
		// 表单登录验证
		$(function(){
			$(".registerform").submit(function(){
				var errors = $("span.error").html();
				if($.trim(errors) != ""){
					$(".register-error").html("请正确完成表单填写！");
					return false;
				}
				// 注册
				var username = $(".div-username").children("input").attr("value");
				var password = $(".div-password").children("input").attr("value");
				var sex = $(".div-sex").children("input").attr("value");
				var birthday = $(".div-birthday").children("input").attr("value");
				var email = $(".div-email").children("input").attr("value");
				var school = $(".div-school").children("input").attr("value");
				var user_pic = $(".div-user-pic").children("input").attr("value");
				var hobby = $(".div-hobby").children("input").attr("value");
				var signature = $(".div-signature").children("textarea").val();
				var params = {
					"username":username,
					"password":password,
					"sex":sex,
					"birthday":birthday,
					"email":email,
					"school":school,
					"image":user_pic,
					"hobby":hobby,
					"signature":signature
				}
				$.ajax({
					url: 'http://sso.icampus.com/service/user/doRegister',
					type: 'post',
					data:params,
					dataType: 'json',
					statusCode:{
						200:function(){
							jQuery.alertWindow("注册成功");
							window.location.href="http://sso.icampus.com/user/login.html";
							return false;
						},
						500:function(){
							jQuery.alertWindow("注册失败");
							return false;
						}
					}
				});
			});
		});	
		// 上传图片显示
		var uploadFile = null;
	    function showPreview(source) {
	        var file = source.files[0];
	        uploadFile = file;
	        if (window.FileReader) {
	            var fr = new FileReader();
	            fr.onloadend = function(e) {
	                document.getElementById("user-pic").src = e.target.result;
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
                    	jQuery.alertWindow("上传成功");
                    }  
                }
            )
            return false;
        }
</script>
</body>
</html>