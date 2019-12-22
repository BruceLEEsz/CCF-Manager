const app = new Vue({
    el: "#changePasswordForm",
    data: {
        oldPassword: '',
        newPassword1: '',
        newPassword2: ''
    },
    methods: {
        changePassword: function () {
            if (this.newPassword1 === this.newPassword2) {
                axios({
                    url: "/Data/changePassword",
                    method: "POST",
                    data: {
                        token: getCookie("token"),
                        params: {
                            oldPassword: this.oldPassword,
                            newPassword: this.newPassword1
                        }
                    }
                }).then(function (rep) {
                    if (rep.status === "SUCCESS") {
                        alert("密码修改成功，请重新登录");
                        logout();
                        window.location.href = "/login.html";
                    } else {
                        alert("密码修改失败，" + rep.data.reason);
                    }
                }, function () {
                    alert("抱歉，服务器当前不可用");
                });
            } else {
                alert("两次密码输入不一致，请重新输入");
            }
        }
    }
});