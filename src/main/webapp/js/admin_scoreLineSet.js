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
                if (rep.data.status === 'SUCCESS') {
                    setCookie("token", rep.data.token);
                    alert("分数线上传成功");
                } else {
                    alert("报名失败" + rep.data.reason);
                }
            }).catch(function(rep){
                alert("抱歉，服务器当前不可用");
            })
        }
    }
});