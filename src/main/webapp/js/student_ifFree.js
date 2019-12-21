const app = new Vue({
    el: "#ifFree",
    data: {
        examId: "19",
        competition: "CCF能力认证考试",
        confirm: "FALSE",
        code: "NONE"
    },
    created(){
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
                    this.examId = rep.data.examId+19;
                    this.competition = rep.data.competition;
                    this.confirm = rep.data.confirm;
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