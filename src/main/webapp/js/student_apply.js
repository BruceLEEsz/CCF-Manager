const app = new Vue({
        el: "#demo-form2",
        data: {
            examId: "19"
        },
        methods: {
            signUp() {
                axios({
                    url: "Data/signUp",
                    method: "POST",
                    data: {
                        token: getCookie("token")
                    }
                }).then(function (rep) {
                    if (rep.data.status === 'SUCCESS') {
                        setCookie("token", rep.data.token);
                        alert("报名成功");
                    } else {
                        alert("报名失败" + rep.data.reason);
                    }
                }, function () {
                    alert("抱歉，网页当前不可用");
                })
            }
        },
        created: function () {
            axios({
                url: "Data/getExamId",
                method: "POST",
                data: {
                    token: getCookie("token")
                }
            }).then(function (rep) {
                if (rep.data.status === 'SUCCESS') {
                    setCookie("token", rep.data.token);
                    this.examId = rep.data.examId;
                } else {
                    console.log("获取竞赛标识失败" + rep.data.reason);
                }
            }, function () {
                alert("抱歉，网页当前不可用");
            });
        }
    })
;