const app = new Vue({
    el: "#ifFree",
    data: {
        examId: "",
        competition: "",
        confirm: "",
        code: ""
    },
    created() {
        this.init();
    },
    methods: {
        init: function () {
            axios({
                url: "/Data/confirm",
                method: "post",
                data: {
                    token: getCookie("token")
                }
            }).then(rep => {
                if ("SUCCESS" === rep.data.status) {
                    setCookie("token", rep.data.token);
                    this.examId = Number(rep.data.examId) + 19;
                    if (rep.data.competition === "") {
                        this.competition = "暂无"
                    } else {
                        this.competition = rep.data.competition;
                    }
                    if (rep.data.confirm) {
                        this.confirm = "是"
                    } else {
                        this.confirm = "否"
                    }

                    this.code = rep.data.code;
                } else {
                    alert("信息加载失败" + rep.data.reason);
                }
            }, function () {
                alert("抱歉，网页当前不可用");
            })
        }
    }
})