var $messages = $("#messages").children("p");
var $cards = $("#cards").children("p");
var userId = $("#username").attr("name");
$(function(){
	$.ajax({
		url:'http://manage.icampus.com/rest/api/card/' + userId + "/1",
		type:'get',
		dataType:'jsonp',
		statusCode:{
			200:function(data){
				$cards.html(data.total);
			}
		}
	});
	$.ajax({
		url:'http://manage.icampus.com/rest/api/message/' + userId + "/1",
		type:'get',
		dataType:'jsonp',
		statusCode:{
			200:function(data){
				$messages.html(data.total);
			}
		}
	});
})