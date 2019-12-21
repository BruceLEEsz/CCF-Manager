const app = new Vue({
    el: "#MessagePost",
    data: {
        competition:""
    },
    methods: {
        release: function () {
            axios({
                url: "/Data/setCompetition",
                method: "post",
                data: {
                    token: getCookie("token"),
                    params: {
                        competition:app.competition
                    }
                }
            }).then(function (rep) {
                if (rep.data.status === 'SUCCESS') {
                    setCookie("token", rep.data.token);
                    alert("竞赛信息发布成功");
                } else {
                    alert("发布失败" + rep.data.reason);
                }
            }).catch(function(rep){
                alert("抱歉，服务器当前不可用");
            })
        }
    }
});