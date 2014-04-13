#输入自动提示#

随着输入进行动态查询，并且动态显示相关的数据在下拉列表中，数据源可以是本地的，比如数组变量，或者是远程的，比如数据库，使用的javascript插件为基于jquery的一款非常酷的plugin，叫做jquery autocomplete，官网在[这里](https://github.com/agarzola/jQueryAutocompletePlugin)，这里有许多[demo](http://jquery.bassistance.de/autocomplete/demo/)，现在已经不再维护了，下面是使用过程中需要注意的几点。

1. jquery版本
	
	github上面下载的最新版本是1.2.3，这个版本修复了，对于高版本jquery不支持$.browser对象的问题，所以使用这个版本的时候可以使用高版本的jquery。

2. 后端数据

	从后端获取数据后，一般是以字符串形式从后天传至前端，由该插件进行解析，解析原则是以字符串中的"\n"符进行分割，分割后的每条记录作为下拉列表中的一行，然后对每行中的字符串以“|”进行分割，分割后的内容可以自定义进行显示，如拼凑html进行显示。

3. formatItem设置

	对该插件进行设置时，config项中的formatItem选项需要传递一个函数过去，其中该函数的第一个参数为解析后的下拉列表中的每项数据，它是一个数组，大小为1，所以获取的时候使用row[0]即可。
			
		formatItem : function(row, i, total){
                var jsonStr = row[0];
                var jsonObj = eval('('+jsonStr+')');
                klass = jsonObj.type;
                switch(klass){
                    case "question":
                        return completeLineQuestion(jsonObj, false);
                        break;
                    case "account":
                        return completeLineAccount(jsonObj, true);
                        break;
                    default:
                        return "";
                        break;
                }
        }

4. 选中某行后，触发动作由result项进行设置，该插件全部使用示例代码如下
	
		search_input.autocomplete("${rc.getContextPath()}/search/all",{
            mincChars: 1,
            delay: 500,
            width: 580,
            scroll : false,
            selectFirst : false,
            clickFire : true,
            hideOnNoResult : false,
            noRecord : "没有找到类似的内容，添加一个问题",
            formatItem : function(row, i, total){
                var jsonStr = row[0];
                var jsonObj = eval('('+jsonStr+')');
                klass = jsonObj.type;
                switch(klass){
                    case "question":
                        return completeLineQuestion(jsonObj, false);
                        break;
                    case "account":
                        return completeLineAccount(jsonObj, true);
                        break;
                    default:
                        return "";
                        break;
                }
            }
        }).result(function(e, row, formatted){
            var url = "${rc.getContextPath()}";
            var jsonStr = row[0];
            var jsonObj = eval('('+jsonStr+')');
            klass = jsonObj.type;
            switch(klass){
                case "question":
                    url = "/question/"+jsonObj.id;
                    break;
                case "account":
                    url = "/profile/"+jsonObj.id;
                    break;
            }
            window.location.href = url;
            return false;

        });
                
5. 展现时，如何加上自定义的html，比如多加上一行?

6. 输入关键词后，在按空格，应该保持搜索结果不变，但是目前按空格后搜索失效

	那是因为没有继续去发起ajax请求，因为缓存判断问题，已修复。

7. 如何让搜索为空的结果提示在最上层显示，而不被遮住

	这是因为js代码出错，对错误提示信息不能转为json对象，解决办法就是将错误提示信息写出一个json字符串形式。

8. 按回车后，输入框中不应该出现js代码，而应该是输入的关键词
	
	屏蔽掉源码第248行即可
		
		//$input.val(v); 屏蔽掉输入框中回显js字符串