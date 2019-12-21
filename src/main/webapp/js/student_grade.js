const app = new Vue({
    el: "#scoresQuery",
    data: {
        allScores: ""
    },
    created() {
        this.init()
    },
    methods: {
        init: function () {
            axios({
                url: "/Data/getScore",
                method: "post",
                data: {
                    token: getCookie("token")
                }
            }).then(rep => {
                if (rep.data.status === "SUCCESS") {
                    setCookie("token", rep.data.token);
                    this.allScores = rep.data.allScores;
                } else {
                    alert("成绩加载失败" + rep.data.reason);
                }
            }, function () {
                alert("抱歉，网页当前不可用");
            })
        }
    }
});