
const app = new Vue({
    el: "#scoresQuery",
    data: {
        allScores: ""
    },
    created() {
        this.init()
    },
    methods: {
        init() {
            axios({
                url: "/Data/getScore",
                method: "post",
                data: {
                    token: getCookie("token")
                }
            }).then(rep => {
                if (rep.token.status === "sucess") {
                    setCookie("token", rep.data.token);
                    this.allScores=rep.data;
                }else{
                    alert("成绩加载失败");
                }
            })
        }
    }
})