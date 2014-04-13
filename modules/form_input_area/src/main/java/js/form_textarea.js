/**
 * Created by yangchangming on 14-4-13.
 */

$(function(){

    /**
     * 问题文本框高度自适应
     */
    $('#content').on("scroll",function(){
        $(this).css('height', $(this)[0].scrollHeight);
    }).on("keyup",function(){
        $(this).attr('style', '');
        $(this).trigger('scroll');
    });

});