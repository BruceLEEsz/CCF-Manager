const app = new Vue({
    el: "#scoreLine",
    data: {
        examscore: ""
    },
    methods: {
        set: function () {
            axios({
                url: "/Data/setScoreLine",
                method: "post",
                data: {
                    token: getCookie("token"),
                    params: {
                        scoreLine: this.examscore
                    }
                }
            }).then(function (rep) {
                if (rep.data.status === "SUCCESS") {
                    setCookie("token", rep.data.token);
                    alert("分数线设置成功");
                } else {
                    alert("分数线设置失败" + rep.data.reason);
                }
            }, function () {
                alert("抱歉，网页当前不可用");
            })
        }
    }
});