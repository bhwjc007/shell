/**
 * 弹出框，模拟遮罩效果
 */

var isIe=(document.all)?true:false; 


/**
 * 设置select的可见状态
 * @param state 可见状态 - "hidden"
 */
function setSelectState(state) 
{ 
 var objl=document.getElementsByTagName('select'); 
 var i=0;
 var j=0;
 for(i=0;i<objl.length;i++) 
 { 
	 objl[i].style.visibility=state; 
 } 
} 


/**
 * 生成位置对象
 * @param ev
 * @returns
 */
function mousePosition(ev) 
{ 
	 if(ev.pageX || ev.pageY) 
	 { 
		 return {x:ev.pageX, y:ev.pageY}; 
	 } 
	 return {x:ev.clientX + document.body.scrollLeft - document.body.clientLeft,y:ev.clientY + document.body.scrollTop - document.body.clientTop }; 
} 


function v_openAlertWin(){
}



/**
 * 模拟弹出框
 * @param wTitle 窗口标题
 * @param content 窗体内加载的内容
 * @param wWidth 窗体宽度
 * @param wHeight 窗体高度
 */
function showMessageWindow(wTitle,content,wWidth,wHeight) { 
	var windowWidth = document.documentElement.clientWidth; 
	//windowHeight = document.documentElement.clientHeight; 
	
	var left = (windowWidth - wWidth) / 2;
    var top =  (150 + document.documentElement.scrollTop);
	
	 closeWindow(); 
	 var bWidth=1600;//parseInt(document.documentElement.scrollWidth); 
	 var bHeight=parseInt(document.documentElement.scrollHeight-20); 
	 if(isIe){ 
		 setSelectState('hidden');
	 } 
	 //创建背景div对象
	 var back=document.createElement("div"); 
	 back.id="back";
	 //背景遮罩css样式
	 var styleStr="z-index:8;top:0px;left:0px;position:absolute;background:#666;width:100%;height:100%;"; 
	 styleStr+=(isIe)?"filter:alpha(opacity=0);":"opacity:0;"; 
	 back.style.cssText=styleStr; 
	 document.body.appendChild(back); 
	 showBackground(back,65); 
	 //创建弹出窗口div对象
	 var mesW=document.createElement("div"); 
	 mesW.id="mesWindow"; 
	 mesW.className="mesWindow"; 
	 
	 mesW.innerHTML="<table width='100%' height='100%' cellSpacing='0' cellPadding='0' bgcolor='#eff5fe' style='border:3px solid #000;'>" +
	 		//标题
	 		"<tr height='30px' style='background-color: #627AAD;' >" +
	 			"<td style='text-align: left; background-color: #627AAD; color:#fff;'>"+wTitle+"</td>" +
	 			"<td style='text-align: right;'>" +
	 				"<input type='button' onclick='closeWindow();' title='关闭窗口' style='cursor:pointer' class='close' value='关闭' />" +
	 			"</td>" +
	 		"</tr>" +
	 		//内容
	 		"<tr width='100%'>" +
	 				"<td colspan='2' style='padding:10px 10;'>" +
	 					
	 				"<iframe name='innerIframe' src='" + content + "' style='border-style: none; overflow:hidden; width: 100%;height: 100%;' id='innerIframe'></iframe>" +
	 				
	 				"</td>" +
	 		"</tr>" +
	 		"</table>"; 
	 
	// mesW.innerHTML="<table width='100%' height='100%' bgcolor='#eff5fe' ><tr height='10px'><td align='left'>"+wTitle+"</td><td  align='right'>&nbsp;</td></tr><tr  width='100%' ><td colspan='2'><p align='center' height='100%'>"+content+"</span></td></tr></table>";
	 
	 //弹出窗口div样式
	// styleStr="z-index:10;left:"+(pos.x-200)+"px;top:"+(pos.y-100)+"px;position:absolute;width:"+wWidth+"px;height:"+wHeight+"px"; 
	 styleStr="z-index:10;left:"+ left +"px;top:"+ top +"px;position:absolute;width:"+wWidth+"px;height:"+wHeight+"px";
	 mesW.style.cssText=styleStr; 
	 document.body.appendChild(mesW); 
} 



/**
 * 遮罩效果
 */
function showBackground(obj,endInt) { 
 if(isIe) 
 { 
	 obj.filters.alpha.opacity+=1; 
	 if(obj.filters.alpha.opacity<endInt) 
	 { 
		 setTimeout(function(){showBackground(obj,endInt)},5); 
	 } 
 }else{ 
	 var al=parseFloat(obj.style.opacity);
	 al+=0.1; 
	 obj.style.opacity=al; 
	 if(al<(endInt/100)) 
	 {
		 setTimeout(function(){showBackground(obj,endInt)},5);
	 } 
 } 
} 
 

/**
 * 关闭窗口
 */
function closeWindow() 
{ 
	 if(document.getElementById('back')!=null) 
	 { 
		 document.getElementById('back').parentNode.removeChild(document.getElementById('back')); 
	 } 
	 if(document.getElementById('mesWindow')!=null) 
	 { 
	     var iframe=document.getElementById("innerIframe");
	     if(iframe!=null){
	    	 iframe.src="about:blank";
	     }        
	     document.getElementById('mesWindow').parentNode.removeChild(document.getElementById('mesWindow')); 
	 } 
	 if(isIe){ 
		 setSelectState('');
	 } 
} 


/**
 * 测试弹出窗口
 * @param ev
 */
function testMessageBox(ev)
{ 
	// var objPos = mousePosition(ev); 
	 //messageContent="<div style='padding:2px 0 2px 0;text-align:center'>钓鱼岛，是你们的，苍老师是我们的！</div>"; 
	 messageContent = "<%=request.getContextPath() %>/web/organization/user/userAdd.jsp";
	 showMessageWindow('钓鱼岛海战',messageContent,700,300); 
} 

