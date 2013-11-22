/**
* 	查询结果表格左右滚动支持脚本，<restable>标签内部要使用此脚本.
* 页面上显示查询结果的表格有时因为字段过多会显得很拥挤，采用分帧技术可以让浏览器自动
* 加上滚动条，但分帧会带来很多麻烦，因此采用了CSS来处理此问题。
* 	目前的解决方法是：查询结果的所有字段都生成到页面里，只是某些列的style的display属性
* 设为none,既不显示出来。当用户点击了向右滚动按钮时，将最左面一列的display设为none,
* 初始隐藏的第一列显示出来，这样就产生了向右"卷滚"了一列的假象。向左滚动也是一样的原理。
* 	可视的几列称为视口(ViewPort)，在视口之外的列display属性都设为none隐藏起来。视口移
* 动后重新设置列的显示隐藏属性
*	有些列要固定，不能随表格一起滚动。DBGrid的实现机制是将这些列的<td>的id属性都设为"fix"
* 当显示或隐藏表格的列时先判断该单元格的id是否为"fix"，如果是则不动它的display属性(值为显示)
* 否则再判断是否在视口内，显示不显示。
*/

/**
* 视口类，表示当前可见的显示区域，用来控制显示查询结果的哪些列.
*/
function ViewPort(){
	this.left = 0;
	this.width = 0;
	this.setLeft = ViewPort_setLeft;
	this.getLeft = ViewPort_getLeft;
	this.setWidth = ViewPort_setWidth;
	this.getWidth = ViewPort_getWidth;
	this.getRight = ViewPort_getRight;
	this.forward = ViewPort_forward;
	this.backward = ViewPort_backward;
	this.isInViewPort = ViewPort_isInViewPort;
}

/**
* 设置视口的左边界列
* param pos 左边界
*/
function ViewPort_setLeft(pos){
	this.left = pos;
}

function ViewPort_getLeft(){
	return this.left;
}

/**
* 返回视口的右边界列
* return 右边界列
*/
function ViewPort_getRight(){
	return this.left + this.width - 1;
}

/**
* 设置视口的宽度
* @param width 宽多少列
*/
function ViewPort_setWidth(width){
	this.width = width;
}

function ViewPort_getWidth(){
	return this.width;
}

/**
* 视口向右滑动一列
*/
function ViewPort_forward(){
	this.left++;
}

/**
* 视口向左滑动一列
*/
function ViewPort_backward(){
	this.left--;
}

/**
* 判断某列是否在视口之内
*/
function ViewPort_isInViewPort(col){
	return col >= this.getLeft() && col <= this.getRight();
}

/**
* Grid类，表示页面上的显示查询结果的表格.
* @param tbl 页面上显示查询结果的<table>对象的ID
* @param rows 显示的列数
*/
function Grid(tbl,cols){
	var count = tbl.rows[0].cells.length;

	//properties
	this.grid = tbl;
	this.viewPort = new ViewPort();

	if(cols == null)
		this.cols = count;
	else
		this.cols = cols;

	this.startCol = 0;
	this.endCol = count - 1;

	//methods
	this.scroll = scroll;
	this.show = show;

	//initialize
	var fixCols = 0;
	//计算显示的第一列是哪列,并累计固定列数
	while(this.startCol < count &&
		   this.grid.rows[0].cells[this.startCol].id == "fix"){
		this.startCol++;
		fixCols++;
	}

	//计算显示的最后一列是哪列,并累计固定列数
	while(this.endCol >= this.startCol &&
		   this.grid.rows[0].cells[this.endCol].id == "fix"){
		this.endCol--;
		fixCols++;
	}

	this.viewPort.setLeft(this.startCol);//视口初始位置

	//视口初始宽度
	if(this.cols - fixCols > 0)
		this.viewPort.setWidth(this.cols - fixCols);
	else
		this.viewPort.setWidth(0);

	this.show();
}

/**
* 向左或向右滚动查询结果表格
* @param direction "left"|"right"
*/
function scroll(direction){
	if(direction == "right"){//向右卷一列
		if(this.viewPort.getRight() < this.endCol){
			this.viewPort.forward();
		}
	}else if(direction == "left"){//向左卷一列
		if(this.viewPort.getLeft() > this.startCol){
			this.viewPort.backward();
		}
	}
	this.show();
}

/**
* 显示查询结果表格，显示在视口内的列，隐藏之外的列
*/
function show(){
	var x = this.grid.rows.length;
	var y = this.grid.rows[0].cells.length;

	//隐藏在viewPort之外的所有非固定列，显示在viewPort之内的所有列
	for(var i = 0; i < x; i++){
		for(var j = 0; j < y; j++){
			with(this.grid.rows[i].cells[j]){
				if(id != "fix"){
					if(this.viewPort.isInViewPort(j)){
						style.display = "";
					}else{
						style.display = "none";
					}
				}
			}
		}
	}
}

