const app = new Vue({
    el: "#VipSetUp",
    data: {
        signUpTime: '',
        scoreLine: ''
    },
    methods: {
        resetSignUpTime() {
            this.signUpTime = ''
        },
        resetScoreLine() {
            this.scoreLine = ''
        },
        addExam() {
            axios({
                url: "/Data/addExam",
                methods: "POST",
                data: {
                    token: getCookie("token"),
                    params:{
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