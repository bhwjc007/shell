/******************************************************
 * @SHELL Verify JS v1.0
 * Javascript 验证函数
 * DateTime: 2012.8.6
 * By changming.Y
 *****************************************************/

/**
 * 校验电话号码录入的有效性,有效返回true,无效返回false
 */
function teleNOVerify(objText,intLength,strName ){
  var strText=objText.value;
  if( strText.length > intLength){
    alert("栏目‘"+strName+"’超过指定长度。");
    objText.focus();
    return false;
  }
	
  var i;
  for(i=0;i<strText.length;i++){
    if((strText.charCodeAt(i)<48 || strText.charCodeAt(i)>57) &&  strText.charCodeAt(i)!=45){
      alert("栏目‘"+strName+"’只能填写数字和连字符‘-’");
      objText.focus();
      return false;
    }
  }
  return true;
}


/**
 * 验证email地址是否合法
 * @param str input
 * @return true if valid
 */
function isMail(str){
	var regExp = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/g;
	return (str.match(regExp) != null);
}

/*
	whether string is a valid mail address list, separate by "," or ";"
	@param str input string
	@return true if valid
*/
function isMailList(str){
	var sepRegExp = /\s*[,|;]\s*/g;
	var mails = str.split(sepRegExp);

	if(mails == null)
		return false;

	for(var i = 0; i < mails.length; i++){
		if(!isMail(mails[i]))
			return false;
	}

	return true;
}

/*
	whether string consist of number
	@param str input string
	@return true if valid
*/
function isNumber(str){
	var regExp = /^[0-9]+$/g;
	return (str.match(regExp) != null);
}


function isDigit(str){
	var _regExp = /^[0-9]$/g;
	return (str.match(_regExp) != null);
}

function isFloat(str){
	return isDouble(str);
}

function isDouble(str){
	var _regExp = /^[\+|-]?[0-9]+(\.[0-9]+)?$/g;
	return (str.match(_regExp) != null);
}

function isInteger(str){
	return isLong(str);
}

function isLong(str){
	var _regExp = /^[\+|-]?[0-9]+$/g;	
	return (str.match(_regExp) != null);
}


function isDate(str)
{
	var _regExp = /^((19)|(20))[0-9]{2}-([0-9]|(0[0-9])|(1[0-2]))-([0-9]|([0-2][0-9])|([3][0-1]))$/g;	
	return (str.match(_regExp) != null);
}

/*
	whether string is null reference or empty string
	@param str input string
	@return true if valid
*/
function isNullOrEmpty(str){
	if(str == null){
		return true;
	}
	if(str.length == 0){
		return true;
	}
	return false;
}


function validateRequired(ctrl, msg){
	if(isNullOrEmpty(ctrl.value)){
		alert(msg + "不能为空");
		ctrl.focus();
		return false;
	}
	return true;
}

function validateMinLength(ctrl, msg, min){
	var value = ctrl.value;
	if(!isNullOrEmpty(value) && value.length < min){
		alert(msg + "的长度不能小于" + min);
		ctrl.focus();
		return false;
	}
	return true;
}

function validateMaxLength(ctrl, msg, max){
	var value = ctrl.value;
	if(!isNullOrEmpty(value) && value.length > max){
		alert(msg + "的长度不能大于" + max);
		ctrl.focus();
		return false;
	}
	return true;
}

function validateInteger(ctrl, msg){
	var value = ctrl.value;
	if(!isNullOrEmpty(value) && !isInteger(value)){
		alert(msg + "的值不是合法的整数");
		ctrl.focus();
		return false;
	}
	return true;
}

function validateLong(ctrl, msg){
	return validateInteger(ctrl, msg);
}


function validateFloat(ctrl, msg){
	var value = ctrl.value;
	if(!isNullOrEmpty(value) && !isFloat(value)){
		alert(msg + "的值不是合法的小数");
		ctrl.focus();
		return false;
	}
	return true;
}

function validateDouble(ctrl, msg){
	return validateFloat(ctrl, msg);
}

function validateDate(ctrl, msg){
	var value = ctrl.value;
	if(!isNullOrEmpty(value) && !isDate(value)){
		alert(msg + "不是合法的日期");
		ctrl.focus();
		return false;
	}
	return true;
}













//校验文本录入的有效性,有效返回true,无效返回false
//objText为待校验的页面表单对象；blnEmpty表示此文本是否可为空,true表示可为空，false表示不可为空；
//intLength最大字节长度；strName校验的对象栏目的名称
function textVerify(objText,blnEmpty,intLength,strName ){
  var strText=objText.value;

  if(strText=="" && blnEmpty==false){
    alert("栏目‘"+strName+"’不能为空。");
    objText.focus();
    return false;
  }
  if(calculateStrLen(strText) > intLength){
    alert("栏目‘"+strName+"’超过了指定的长度，请更正。");
    objText.focus();
    return false;
  }
  return true;
}


//校验纯字母文本录入的有效性,有效返回true,无效返回false
function charVerify(objText,blnEmpty,intLength,strName ){
  var strText=objText.value;
  if(strText=="" && blnEmpty==false){
    alert("栏目‘"+strName+"’不能为空。");
    objText.focus();
    return false;
  }
  var i;
  for(i=0;i<strText.length;i++){
    if(strText.charCodeAt(i)!=32){
      if(strText.charCodeAt(i)<65 || ( strText.charCodeAt(i)>90 && strText.charCodeAt(i)<97 )  ||  strText.charCodeAt(i)>122 ){
        alert("栏目‘"+strName+"’只能填写字母。");
        objText.focus();
        return false;
      }
    }
  }
  if(strText.length > intLength){
    alert("栏目‘"+strName+"’超过了指定的长度，请更正。");
    objText.focus();
    return false;
  }
  return true;
}

function char_numVerify(objText,blnEmpty,intLength,strName ){
//校验纯字母和数字文本录入的有效性,有效返回true,无效返回false
  var strText=objText.value;
  if(strText=="" && blnEmpty==false){
    alert("栏目‘"+strName+"’不能为空。");
    objText.focus();
    return false;
  }
  var i;
  for(i=0;i<strText.length;i++){
    if(strText.charCodeAt(i)!=32){
      if(strText.charCodeAt(i)<48 || ( strText.charCodeAt(i)>57 && strText.charCodeAt(i)<65 )  || ( strText.charCodeAt(i)>90 && strText.charCodeAt(i)<97 )  ||  strText.charCodeAt(i)>122 ){
        alert("栏目‘"+strName+"’只能填写字母和数字。");
        objText.focus();
        return false;
      }
    }
  }
  if(strText.length > intLength){
    alert("栏目‘"+strName+"’超过了指定的长度，请更正。");
    objText.focus();
    return false;
  }
  return true;
}

function numberVerify(objText,blnEmpty,intLength,strName ){
//校验纯数字文本录入的有效性,有效返回true,无效返回false
  var strText=objText.value;
  if(strText=="" && blnEmpty==false){
    alert("栏目‘"+strName+"’不能为空。");
    objText.focus();
    return false;
  }
  var i;
  for(i=0;i<strText.length;i++){
    if((strText.charCodeAt(i)<48 || strText.charCodeAt(i)>57)){
      alert("栏目‘"+strName+"’只能填写数字。");
      objText.focus();
      return false;
    }
  }
  if(strText.length > intLength){
    alert("栏目‘"+strName+"’超过了指定的长度，请更正。");
    objText.focus();
    return false;
  }
  return true;
}

function intVerify(objText,blnEmpty,intLength,strName ){
//校验整数文本录入的有效性,有效返回true,无效返回false
  var strText=objText.value;
  if(strText=="" && blnEmpty==false){
    alert("栏目‘"+strName+"’不能为空。");
    objText.focus();
    return false;
  }
  var i;
  for(i=0;i<strText.length;i++){
    if((strText.charCodeAt(i)<48 || strText.charCodeAt(i)>57)){
      alert("栏目‘"+strName+"’只能填写整数。");
      objText.focus();
      return false;
    }
  }
  if(strText.length > intLength){
    alert("栏目‘"+strName+"’超过了指定的长度，请更正。");
    objText.focus();
    return false;
  }
  return true;
}

function doubleVerify(objText,blnEmpty,totalLen,decimalLen,strName ){
//校验带小数数字文本录入的有效性,有效返回true,无效返回false
  var strText=objText.value;
  if(strText=="" && blnEmpty==false){
    alert("栏目‘"+strName+"’不能为空。");
    objText.focus();
    return false;
  }
  var i;
  for(i=0;i<strText.length;i++){
    if((strText.charCodeAt(i)<48 || strText.charCodeAt(i)>57) &&  strText.charCodeAt(i)!=46&&  strText.charCodeAt(i)!=69){
      alert("栏目‘"+strName+"’只能填写数字。");
      objText.focus();
      return false;
    }
  }

  var point=strText.indexOf('.');
  if( point==-1){
    if(strText.length > totalLen-decimalLen){
      alert("栏目‘"+strName+"’输入的数字过大，请更正。");
      objText.focus();
      return false;
    }
  }else{
    if(point > totalLen-decimalLen){
      alert("栏目‘"+strName+"’输入的数字过大，请更正。");
      objText.focus();
      return false;
    }
  }  
  return true;
}

function dateOrderVerify(objStart,objEnd,blnEmpty,strStartName,strEndName ){
//校验关联日期的有效性,有效返回true,无效返回false
  var strStart=objStart.value;
  var strEnd=objEnd.value;
  if(blnEmpty){
    if(strStart=="" || strEnd==""){
      return true;
    }
  }else{
    if(strStart==""){
      alert("栏目‘"+strStartName+"’不能为空。");
      objStart.focus();
      return false;
    }
    if(strEnd==""){
      alert("栏目‘"+strEndName+"’不能为空。");
      objEnd.focus();
      return false;
    }
  }

  var intStart=(strStart.substring(0,4))*10000+(strStart.substring(5,7))*100+(strStart.substring(8,10));
  var intEnd=(strEnd.substring(0,4))*10000+(strEnd.substring(5,7))*100+(strEnd.substring(8,10));
  if(intStart>intEnd){
    alert("栏目‘"+strStartName+"’指定的日期不能比栏目‘"+strEndName+"’指定的日期晚。");
    objStart.focus();
    return false;
  }else{
    return true;
  }
}




// 检查 Select 是否为空，不为空返回true,空返回false
function selectVerify(objText,strName){
  if(objText != null){
    if(objText.value != ""){
      return true;
    }
    alert("栏目‘"+strName+"’不能为空。");
    objText.focus();
    return false;
  }else{
    alert("栏目‘"+strName+"’不存在。");
    return false;
  }
}


//校验日期的有效性,有效返回true,无效返回false
function dateVerify(objText,blnEmpty,strFormat,strName){

  var strText=objText.value;
  if(strText=="" && blnEmpty==false){
    alert("栏目‘"+strName+"’不能为空。");
    objText.focus();
    return false;
  }
  if(strText.length!=strFormat.length){
    alert("栏目‘"+strName+"’的格式不正确,请按照‘"+strFormat+"’格式输入日期。");
    objText.focus();
    return false;
  }
  
  var i,j;
  var strFormatTemp;
  var strTemp;
  
  for(i=0;i<strText.length;){
  	strFormatTemp=strFormat.substring(i,i+4);
  	if(strFormatTemp=="yyyy"){
  	  strTemp=strText.substring(i,i+4);
  	  for(j=0;j<4;j++){
        if(strTemp.charCodeAt(j)<48 || strTemp.charCodeAt(j)>57){
          alert("栏目‘"+strName+"’的格式不正确,请按照‘"+strFormat+"’格式输入日期。");
          objText.focus();
          return false;
        }
  	  }
  	  i=i+4;
    }else{
  	
  	strFormatTemp=strFormat.substring(i,i+2);
  	if(strFormatTemp=="MM" || strFormatTemp=="dd" || strFormatTemp=="HH" || strFormatTemp=="mm" || strFormatTemp=="ss"){
  	  strTemp=strText.substring(i,i+2);
  	  for(j=0;j<2;j++){
        if(strTemp.charCodeAt(j)<48 || strTemp.charCodeAt(j)>57){
          alert("栏目‘"+strName+"’的格式不正确,请按照‘"+strFormat+"’格式输入日期。");
          objText.focus();
          return false;
        }
  	  }
	  if(strFormatTemp=="MM" && (strTemp>12 || strTemp<1)){
          alert("栏目‘"+strName+"’的月份输入不正确,请按照‘"+strFormat+"’格式输入日期。");
          objText.focus();
          return false;
	  }
	  if(strFormatTemp=="dd" && (strTemp>31 || strTemp<1)){
          alert("栏目‘"+strName+"’的日期输入不正确,请按照‘"+strFormat+"’格式输入日期。");
          objText.focus();
          return false;
	  }
	  if(strFormatTemp=="HH" && (strTemp>24 || strTemp<0)){
          alert("栏目‘"+strName+"’的小时输入不正确,请按照‘"+strFormat+"’格式输入日期。");
          objText.focus();
          return false;
	  }
	  if((strFormatTemp=="mm" || strFormatTemp=="ss") && (strTemp>59 || strTemp<0)){
          alert("栏目‘"+strName+"’的时间输入不正确,请按照‘"+strFormat+"’格式输入日期。");
          objText.focus();
          return false;
	  }
  	  i=i+2;
    }else{
      if(strText.substring(i,i+1)!=strFormat.substring(i,i+1)){
        alert("栏目‘"+strName+"’的格式不正确,请按照‘"+strFormat+"’格式输入日期。");
        objText.focus();
        return false;
      }
  	  i=i+1;
    }
    }
  }
  return true;
}