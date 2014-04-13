/**
 * Created by yangchangming on 14-4-13.
 */

var search_input = $('input.search-query');
try {
    search_input.autocomplete("/search/all?type=all",{
        mincChars: 1,
        delay: 500,
        width: 580,
        scroll : false,
        selectFirst : false,
        clickFire : true,
        hideOnNoResult : false,
        noRecord : "{'type':'noRecord','content':'没有找到类似的内容'}",
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
                case "noRecord":
                    return jsonObj.content;
                    break;
                default:
                    return "";
                    break;
            }
        }
    }).result(function(e, row, formatted){
        var url = "/";
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

}catch(e){
    alert(e.message);
}

