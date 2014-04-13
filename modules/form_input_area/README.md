#表单多行文本输入框#


使用textarea实现表单的多行文本输入框时，如果将textarea元素放在form元素范围内，textarea元素很容易溢出，这是由于其padding属性导致的，即当该属性值大于零时，textarea显示会超出form元素的范围。
   
1. 去除溢出

   在textarea外面再嵌套一层div，并且将textarea的padding属性设为0，输入框的边框效果使用该div的边框实现，而textarea的边框隐藏。
   
2. 文本输入框高度随内容自增加

   这种情况可以借助jquery动态计算文本框的高度，再将高度值赋给当前文本框。即每次按键后开始触发scroll事件，调用scroll handler，将出现滚动条后的文本框高度值赋给当前文本框。
