const app = new Vue({
    el: "#exportBtn",
    methods: {
        downloadSignUpList: function () {
            axios({
                url: "/Data/downloadSignUpList",
                method: "POST",
                data: {
                    token: getCookie("token")
                }
            }).then(function (rep) {
                if (rep.data.status === 'SUCCESS') {
                    setCookie("token", rep.data.token);
                    window.location.href = "/File/download?file=" + rep.data.UUID;
                } else {
                    alert("获取报名名单失败" + rep.data.reason);
                }
            }, function () {
                alert("抱歉，服务器当前不可用");
            })
        }
    }
})