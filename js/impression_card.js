// 加载用户名
function getUsername() {
	$(".username").each(function(i,element){
		var $this = $(this);
		$.ajax({
			url:'http://manage.icampus.com/rest/api/user/' + $this.html(),
			type:'get',
			dataType:'jsonp',
			statusCode:{
				200:function(data){
					$this.html(data.username);
				}
			}
		});
	});
}

function addEvent(){
	// 处理卡片的部分
		var user;
		var img;
		var nums;
		// 隐藏部分标题
		function detachCard(){
			// 隐藏用户标签
			user = $(".card-pic-title").children(".card-pic-user").detach();
			// 隐藏点赞图标
			img = $(".card-pic-title").children("img").detach();
			// 隐藏点赞数
			nums = $(".card-pic-title").children("span").detach();
		}
		// 获取某个标题在全部数量中的索引（数量过大会有问题）
		function findIndex(data, target){
			for(var i = 0; i < data.length; i++){
				if(target == data[i]){
					return i;
				}
			}
		}
		// 给心形绑定一个事件
			$(".xin").bind("click", function(){
				var $this = $(this);
				// 获取card_id
				var card_id = $this.prev(".card-pic-user").attr("name");
				// 获取点赞数
				var stars = Number($this.next("span").html());
				$.ajax({
					url: 'http://manage.icampus.com/rest/api/card/star/' + card_id,
					type: 'get',
					dataType: 'jsonp',
					data: {stars:stars},
					statusCode:{
						200:function(){
							$this.next("span").html(stars + 1);
							$this.attr("src","http://static.icampus.com/image/xin-stared.png");
							console.log("success");
						},
						500:function(){
							alert("服务器内部错误！");
						},
						404:function(){
							alert("没有找到资源！");
						}
					}
				})
			});
			detachCard();
			$(".card-pic-title").hover(
				function(event) {
					var cardTitles = $(".card-pic-title");
					var $this = $(this);
					var index = findIndex(cardTitles, $this[0]);
					$this.css({"background":"#000","height":"55px"});
					$this.children("p").css("font","13px/25px 微软雅黑");
					$this.append(user[index]);
					$this.append(img[index]);
					$this.append(nums[index]);
				},
				function(event) {
					var $this = $(this);
					$this.css({"background":"rgba(4,4,4,0.8)","height":"30px"});
					$this.children("p").css("font","13px/30px 微软雅黑");
					$this.children(".card-pic-user").detach();
			        $this.children("img").detach();
			        $this.children("span").detach();
				});
		// 给图片绑定一个图片浏览功能
			$(".card-con a").lightBox({
				overlayBgColor: "#999", //图片浏览时的背景色
	            overlayOpacity: 0.5, //背景色的透明度
	            containerResizeSpeed: 600 //图片切换时的速度
			});
}