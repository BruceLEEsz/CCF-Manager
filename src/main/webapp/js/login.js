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
                url: "/Data/login",
                method: "post",
                data: {
                    params: {
                        username: this.username,
                        password: this.password
                    }
                }
            }).then(rep => {
                if (rep.data.status === "success") {
                    setCookie("token", rep.data.token);
                    alert("登陆成功");

                    let array = rep.data.token.split('.');
                    //json数据段中传入为student
                    const decodeToken = array[1].fromBase64();
                    if (decodeToken.userType === "STUDENT") {
                        window.location.href = "../student_home.html"
                    } else {//json数据段中传入为Administration
                        window.location.href = "../admin_home.html"
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