const app = new Vue({
    el: "#VipSetUp",
    data: {
        signUpTime: '',
        scoreLine: ''
    },
    methods: {
        resetSignUpTime: function () {
            this.signUpTime = ''
        },
        resetScoreLine: function () {
            this.scoreLine = ''
        },
        addExam: function () {
            console.log("assss");
            axios({
                url: "/Data/addExam",
                method: "POST",
                data: {
                    token: getCookie("token"),
                    params: {
                        signUpTime: this.signUpTime,
                        scoreLine: this.scoreLine
                    }
                }
            }).then(function (rep) {
                if (rep.data.status === 'SUCCESS') {
                    setCookie("token", rep.data.token);
                    alert("新增选拔信息成功");
                } else {
                    alert("新增选拔信息失败" + rep.data.reason);
                }
            }, function () {
                alert("抱歉，服务当前不可用");
            })
        }
    }
});