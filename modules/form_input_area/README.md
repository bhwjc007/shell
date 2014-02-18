表单多行文本输入框
===========

	使用textarea实现表单多行文本的输入框时，在form范围内，textarea很容易溢出，这是由于其padding属性导致的，即当该属性值大于零时，textarea显示会超出form的范围。
   
1. 去除溢出

   在textarea外面在嵌套一层div，并且将textarea的padding属性设为0，输入框的边框效果使用该div显示，而textarea的边框隐藏即可
   
2. 随内容增加输入框高度自动增加