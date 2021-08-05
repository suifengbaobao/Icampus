var IC = ICAMPUS = {
	checkLogin : function(){
		var _token = $.cookie("ICAMPUS_TOKEN");// ??????????cookie
		if(!_token){
			return ;
		}
		$.ajax({
			url : "http://sso.icampus.com/service/user/" + _token,
			dataType : "jsonp",
			type : "GET",
			success : function(data){
				var html = "<a id=\"portrait-link\" href=\"http://www.icampus.com/person.html\"><img id=\"portrait\" src=\"" + data.image + "\"></a>" +
					       "<a style=\"color:green;\" href=\"http://sso.icampus.com/service/user/logout/" + _token + "\"> out\>\></a>";
				$(".nav-con-right").html(html);
			}
		});
	}
}

$(function(){
	IC.checkLogin();
});