const app = new Vue({
    el: "#scoreLine",
    data: {
        examscore: ""
    },
    methods: {
        set() {
            axios({
                url: "/Data/setScoreLine",
                method: "post",
                data: {
                    token: getCookie("token"),
                    params: {
                        examscore: this.examscore
                    }
                }
            }).then(function (rep) {
                if (rep.data.stauts === "SUCCESS") {
                    setCookie("token", rep.data.token);
                    alert("分数线上传成功");
                } else {
                    alert("报名失败" + rep.data.reason);
                }
            }, function () {
                alert("抱歉，网页当前不可用");
            })
        }
    }
});