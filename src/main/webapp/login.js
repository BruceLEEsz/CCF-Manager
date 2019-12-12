const token = getCookie("token");
const app = new Vue({
    el: "#login",
    data: {
        username: "",
        password: ""
    },
    methods: {
        s_login() {
            console.log(this.username + this.password);
            axios({
                url: "/Data/s_login",
                method: "post",
                Data: {
                    username: this.username,
                    password: this.password
                }
            }).then(rep => {
                if (rep.data.status === "success") {
                    setCookie("token", rep.data.token);
                    alert("登陆成功");
                    window.location.href = "src/main/webapp/s_home.html";
                } else {
                    //console.log(rep.data.reason)
                    alert("登陆失败" + rep.data.reason)
                }
            }, rep => {
                alert("抱歉，网页当前不可用");
            })
        }
        ,
        a_login() {
            console.log(this.username + this.password);
            axios({
                url: "/Data/a_login",
                method: "post",
                Data: {
                    username: this.username,
                    password: this.password
                }
            }).then(rep => {
                if (rep.data.status === "success") {
                    setCookie("token", rep.data.token);
                    alert("登录成功");
                    window.location.href = "src/main/webapp/a_home.html"
                } else {
                    alert("登录失败" + rep.data.reason)
                }
            }, rep => {
                alert("抱歉，网页当前不可用");
            })
        }
    }
})