/******************************************************
 * @SHELL Tree JS v1.0
 * 
 * Javascript 树工具类
 * DateTime: 2012.10.25
 * By changming.Y
 *****************************************************/

/**
 * 返回的树节点名称，全局变量
 * 形如： '系统功能 > 工单系统 > 受理工单'
 */
var returnName="";


/**
 * 树节点展开、收缩
 */
function change(){
	if (getSrcElement().id=="foldheader" || getSrcElement().id=="deptheader") {
        var nested = document.getElementById("DIV_" + getSrcElement().nextSibling.nextSibling.id);
        if (nested.style.display=="none") {
            nested.style.display='';
            //TODO 应用root地址不应该写死
            getSrcElement().src="http://localhost:8080/framework/images/tree_minus.gif";
        }else {
            nested.style.display="none";
            getSrcElement().src="http://localhost:8080/framework/images/tree_plus.gif";
        }
    }
}


/**
 * 递归获取指定树节点及其所有祖先节点名称 - have a bug.
 * 返回值赋给全局变量returnName，形如： '系统功能 > 工单系统 > 受理工单'
 * @param obj 树节点对象
 */
function findAncestors(obj) {
	try{
		var parent = obj.parentNode;
		if (obj.previousSibling.previousSibling.id == "foldheader")
			parent = parent.parentNode;
		var foldheader = parent.previousSibling;
		returnName = findHeaderText(foldheader) + " > " + returnName;
		findAncestors(foldheader);
	}
	catch (e) {}
}

/**
 * 获取对象的内部html
 * @param obj
 * @return
 */
function findHeaderText(obj) {
	return obj.lastChild.previousSibling.innerHTML;
}