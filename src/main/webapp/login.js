const token = getCookie("token");
const app = new Vue({
    el: "#login",
    data: {
        username: "",
        password: ""
    },
    methods: {
        login() {
            console.log(this.username + this.password);
            axios({
                url: "/Data/s_login",
                method: "post",
                Data: {
                    params:{
                        username: this.username,
                        password: this.password
                    }
                }
            }).then(rep => {
                if (rep.data.status === "success") {
                    setCookie("token", rep.data.token);
                    alert("登陆成功");
                    const decodeToken = rep.data.token.fromBase64();
                    let array = decodeToken.split(".");
                    //json数据段中传入为student
                    if (array[1].indexOf(student) !== -1) {
                        window.location.href = "src/main/webapp/student_home.html"
                    } else {//json数据段中传入为Administration
                        window.location.href = ""
                    }
                } else {
                    //console.log(rep.data.reason)
                    alert("登陆失败" + rep.data.reason)
                }
            }, function () {
                alert("抱歉，网页当前不可用");
            })
        }
    }
});