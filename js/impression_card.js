// �����û���
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
	// ����Ƭ�Ĳ���
		var user;
		var img;
		var nums;
		// ���ز��ֱ���
		function detachCard(){
			// �����û���ǩ
			user = $(".card-pic-title").children(".card-pic-user").detach();
			// ���ص���ͼ��
			img = $(".card-pic-title").children("img").detach();
			// ���ص�����
			nums = $(".card-pic-title").children("span").detach();
		}
		// ��ȡĳ��������ȫ�������е���������������������⣩
		function findIndex(data, target){
			for(var i = 0; i < data.length; i++){
				if(target == data[i]){
					return i;
				}
			}
		}
		// �����ΰ�һ���¼�
			$(".xin").bind("click", function(){
				var $this = $(this);
				// ��ȡcard_id
				var card_id = $this.prev(".card-pic-user").attr("name");
				// ��ȡ������
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
							alert("�������ڲ�����");
						},
						404:function(){
							alert("û���ҵ���Դ��");
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
					$this.children("p").css("font","13px/25px ΢���ź�");
					$this.append(user[index]);
					$this.append(img[index]);
					$this.append(nums[index]);
				},
				function(event) {
					var $this = $(this);
					$this.css({"background":"rgba(4,4,4,0.8)","height":"30px"});
					$this.children("p").css("font","13px/30px ΢���ź�");
					$this.children(".card-pic-user").detach();
			        $this.children("img").detach();
			        $this.children("span").detach();
				});
		// ��ͼƬ��һ��ͼƬ�������
			$(".card-con a").lightBox({
				overlayBgColor: "#999", //ͼƬ���ʱ�ı���ɫ
	            overlayOpacity: 0.5, //����ɫ��͸����
	            containerResizeSpeed: 600 //ͼƬ�л�ʱ���ٶ�
			});
}