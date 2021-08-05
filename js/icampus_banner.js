/*
* @Author: suife
* @Date:   2017-04-13 21:06:18
* @Last Modified by:   suife
* @Last Modified time: 2017-04-13 21:11:56
*/

    var oContainer = document.getElementById('banner');
    var oContent = document.getElementById('banner-con');
    var aImg = document.getElementsByClassName('img');
    var oTab = document.getElementById('tab');
    var oNext = document.getElementById('next');
    var oPrev = document.getElementById('prev');

    var sHtml = '';
    for(var i=0; i<aImg.length; i++){
        var oLi = document.createElement('li');
         oLi.innerHTML = i+1;
         if(i == 0){
         oLi.className = 'selected';
         }
         oTab.appendChild(oLi);
        sHtml += '<li '+(i==0?'class="selected"':'')+'></li>';
    }
    oTab.innerHTML = sHtml;

    var oCloneImg = oContent.children[0].cloneNode(true);
    oContent.appendChild(oCloneImg);

    oContent.style.width = aImg[0].offsetWidth * aImg.length + 'px';

    function changeImg(idx){
        iNow = idx;
        for(var i=0; i<aLi.length; i++){
            aLi[i].className = '';
        }
        aLi[idx == aImg.length-1?0:idx].className = 'selected';

        animate(oContent, {
            left: -idx * aImg[0].offsetWidth
        });
    }
    var iNow = 0;//代表当前正在显示的图片的索引
    var aLi = oTab.children;
    for(var i=0; i<aLi.length; i++){
        aLi[i].index = i;
        aLi[i].onmouseover = function(){
            changeImg(this.index);
        };
    }

    oNext.onclick = oPrev.onclick = function(){
        if(this == oNext){//next
            iNow++;
            if(iNow == aImg.length){
                oContent.style.left = 0;
                iNow = 1;
            }
            changeImg(iNow);
        }else{//prev
            iNow--;
            if(iNow == -1){
                iNow = aImg.length - 1;
            }
            changeImg(iNow);
        }
    };

    var timer;
    function run(){
        timer = self.setInterval(function(){
            oNext.onclick();
        },2000);
    }
    run();
    oContainer.onmouseover = function(){
        clearInterval(timer);
    };
    oContainer.onmouseout = function(){
        run();
    };
