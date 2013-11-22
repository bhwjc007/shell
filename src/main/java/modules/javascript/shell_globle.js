/******************************************************
 * @SHELL Gloable JS v1.0
 *
 * DateTime: 2012.7.9
 * By changming.Y
 *****************************************************/

var pageWidth = $(document).width(); 
var initWidthValue =  2*(pageWidth/3);
var initHeightValue = 400;

/**
 * 表单提交通用函数
 * @param _currentPage 当前页码
 * @param _action form的action
 * @param _formName form的名称
 * @returns {Boolean}
 */
function doSubmit(_currentPage,_action,_formName){
	if(_currentPage!=null && _currentPage>0){
		document.getElementById("currentPage").value = _currentPage;
	}
	if(_action==null || _action==undefined){
		_action = document.forms[0].action;
		if(_action==null || _action==undefined){
			alert('Action Undefied!');
			throw 'Action Undefied!';
		}
	}
	if(_formName==null || _formName==undefined){
		//默认使用当前页面第一个form表单
		_formName = document.forms[0].name;
	}
	
	try{
		var _form = document.forms[_formName];
		with(_form){
			action = _action;
			submit();
		}
	}catch(error){
		alert(error.name + ":" + error.message);
		return false;
	}
}

/**
 * JQUERY AJAX加载 数据/页面 到指定区域
 * @param url 请求的url或者页面地址
 * @param pageContainerID 装载页面容器id
 */
function loadDataFunc(url,pageContainerID){
	$('#'+pageContainerID).load(url);
}

/**
 * 模拟弹出模态窗口
 * @param _loadURL 弹出窗加载url地址
 * @param _titleTXT 窗口标题文字
 * @param _reloadFunc 刷新窗口函数
 */
function popWindow(_loadURL,_titleTXT,_reloadFunc){
	$.fn.jmodal({
        //弹出DIV层的【确定】按钮触发事件
        data: { callBack: function(){}},
        //刷新原页面
        reload: _reloadFunc,
        title: _titleTXT,
        // e为弹出div中加载页面的div对象，动态生成一个iframe，加载页面放入iframe中
        content: function(e) {  
            e.html('数据加载中...');
            e.html('<iframe name="innerIframe" style="border-style: none; overflow:hidden; width: 100%;height:' + initHeightValue +';" id="innerIframe"></iframe>');
            var innerIframeObj = document.getElementById("innerIframe");
            innerIframeObj.src = _loadURL;
            //e.load(_loadURL);
        },
        //这种按钮已经去除，代替的是载入页面的自定义按钮实现
        buttonText: { ok: '确定', cancel: '取消' },
        initWidth:initWidthValue,
        initHeight:initHeightValue,
        fixed: false, 							//相对于父层容器定位，否则相对于浏览器窗口定位
        okEvent: function(obj, args) {
        	//obj.callBack(); //确定按钮的回调函数
        	args.complete(); //卸载弹出DIV
        }
    });
}


/**
 * 改变表格行背景颜色
 * @param trId 行id
 */
function doChangeColor(trId){
	for( i=0; i < 10 ;i++ ){
		var curId = "color" + i;
		if(curId == trId){
			document.getElementById("color"+i).bgColor="#F7F7CC";
		}else{
			if(document.getElementById("color"+i)!=null )
				document.getElementById("color"+i).bgColor="#FFFFFF";
		}
	}
}


/**
 * 新建模态窗口，位于屏幕中央
 * 无菜单栏 无工具栏 有标题栏
 */
function openNewWindow(xURL, xwidth, xheight) {
	//var showx = (window.screen.availWidth - xwidth) / 2;
	//var showy = (window.screen.availHeight - xheight) / 2;
	var windowWidth = document.documentElement.clientWidth; 
	var showx = (windowWidth - xwidth) / 2;
    var showy =  (150 + document.documentElement.scrollTop);
	//not for chrome
	//window.open(xURL, "", "resizable=no,height=" + xheight + ",width=" + xwidth + ",left=" + showx + ",top=" + showy + ",menubar=no,scrollbars=yes,toolbar=no,titlebar=yes");
    return window.showModalDialog(xURL,"","dialogWidth="+ xwidth +"px;dialogHeight="+ xheight +"px;status=no;help=no;scroll=no");
}
