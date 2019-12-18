const app = new Vue({
    el: "#code",
    data: {
        code: ''
    },
    methods: {
        set: function () {
            axios({
                url: "/Data/setCode",
                method: "POST",
                data: {
                    token: getCookie("token"),
                    params: {
                        code: this.code
                    }
                }
            }).then(function (rep) {
                if (rep.status === 'SUCCESS') {
                    setCookie("token", rep.data.token);
                    alert("团报码发布成功");
                } else {
                    alert("团报码发布失败" + rep.data.reason);
                }
            }, function () {
                alert("抱歉，服务器当前不可用");
            })
        }
    }
});