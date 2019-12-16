function getCookie(c_name) {
    let c_start;
    let c_end;
    //检查cookie是否存在
    if (document.cookie.length > 0) {
        //检查c_name是否存在
        c_start = document.cookie.indexOf(c_name + "=");
        if (c_start !== -1) {
            c_start += c_name.length + 1;
            //获取结束位置
            c_end = document.cookie.indexOf(";", c_start);
            if (c_end === -1) //最后一个cookie
                c_end = document.cookie.length;
            //unescape:对escape进行解码
            return unescape(document.cookie.substring(c_start, c_end))
        }
    }
    return ""
}

function setCookie(c_name, value) {
    const expiredate = new Date(); //初始化
    expiredate.setTime(expiredate.getTime() + 30 * 60 * 1000); //设置时间，30分钟
    document.cookie = c_name + "=" + escape(value) +
        ";expires=" + expiredate.toGMTString() + ";path=/";
    //path表示当前项目页面下有效
}