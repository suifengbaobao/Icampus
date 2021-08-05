$(function () {
	// 表单用户名参数验证
	var reg1 = new RegExp(/[@#\$%\^&\*]/); // 验证非法字符
	$(".div-username").children("input").blur(function () {
		var username = $(".div-username").children("input").attr("value");
		if (reg1.test(username) || $.trim(username) == "") {
			$(".div-username").children("span").html("用户名包含非法字符或为空");
		} else if (username.length > 15) {
			$(".div-username").children("span").html("用户名过长,应<=30个字符");
		} else {
			check(username, 1);
		}
	});
	// 验证邮箱
	var reg2 = new RegExp(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/); // 验证邮箱格式
	$(".div-email").children("input").blur(function () {
		var email = $(".div-email").children("input").attr("value");
		if (!reg2.test(email) || $.trim(email) == "") {
			$(".div-email").children("span").html("邮箱格式不正确或为空");
		} else {
			check(email, 2);
		}
	});
	// 表单密码验证
	var reg3 = new RegExp(/[A-Za-z].*[0-9]|[0-9].*[A-Za-z]/); // 验证密码组成
	$(".div-password").children("input").blur(function () {
		var password = $(".div-password").children("input").attr("value");
		if (!reg3.test(password) || $.trim(password) == "") {
			$(".div-password").children("span").html("密码应由数字和字母组成");
		} else if (password.length < 6 || password.length > 20) {
			$(".div-password").children("span").html("密码长度应该在6-20");
		} else {
			$(".div-password").children("span").css("background", "url(http://static.icampus.com/image/pass.png) no-repeat left 11px")
		}
	});
	// 学校输入验证
	$(".div-school").children("input").blur(function () {
		var school = $(".div-school").children("input").attr("value");
		if ($.trim(school) == "") {
			$(".div-school").children("span").html("不能为空");
		} else {
			$(".div-school").children("span").css("background", "url(http://static.icampus.com/image/pass.png) no-repeat left 11px")
		}
	});
	// 验证两次密码相同
	$(".div-repassword").children("input").blur(function () {
		var password = $(".div-password").children("input").attr("value");
		var repassword = $(".div-repassword").children("input").attr("value");
		if (password != repassword || $.trim(repassword) == "") {
			$(".div-repassword").children("span").html("两次密码输入不一样");
		} else {
			$(".div-repassword").children("span").css("background", "url(http://static.icampus.com/image/pass.png) no-repeat left 11px")
		}
	});

	// 清楚错误提示，正确提示
	$(".div-repassword").children("input").focus(function () {
		$(".div-repassword").children("span").html("");
		$(".div-repassword").children("span").css("background", "")
	});
	$(".div-password").children("input").focus(function () {
		$(".div-password").children("span").html("");
		$(".div-password").children("span").css("background", "")
	});
	$(".div-username").children("input").focus(function () {
		$(".div-username").children("span").html("");
		$(".div-username").children("span").css("background", "")
	});
	$(".div-email").children("input").focus(function () {
		$(".div-email").children("span").html("");
		$(".div-email").children("span").css("background", "")
	});
	$(".div-school").children("input").focus(function () {
		$(".div-school").children("span").html("");
		$(".div-school").children("span").css("background", "")
	});
});
//向后台发起请求，验证邮箱和用户名是否已注册
function check(param, type) {
	$.ajax({
		url: '/service/user/' + param + "/" + type,
		type: 'get',
		dataType:'json',
		success:function(data){
			if(type == 1){
				if (data == false) {
					$(".div-username").children("span").html("该用户名已存在");
				} else {
					$(".div-username").children("span").css("background", "url(http://static.icampus.com/image/pass.png) no-repeat left 11px");
				}
			}
			if(type == 2){
				if (data == false) {
					$(".div-email").children("span").html("该邮箱已注册");
				} else {
					$(".div-email").children("span").css("background", "url(http://static.icampus.com/image/pass.png) no-repeat left 11px")
				}
			}
		}
	});
}
