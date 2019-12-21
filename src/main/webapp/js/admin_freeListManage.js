const app = new Vue({
    el: "#freeListTable",
    data: {
        students: '',
        chooseAll: false
    },
    methods: {
        setQualification: function (studentID) {
            axios({
                url: "/Data/setQualification",
                method: "POST",
                data: {
                    token: getCookie("token"),
                    params: {
                        studentID: studentID
                    }
                }
            }).then(function (rep) {
                if (rep.data.status === "SUCCESS") {
                    setCookie("token", rep.data.token);
                    alert("免费资格设置成功");
                    window.location.href = "/admin_freeListManage.html"
                } else {
                    alert("免费资格设置失败" + rep.data.reason);
                }
            }, function () {
                alert("抱歉，服务器当前不可用");
            })
        },
        deleteQualification: function (studentID) {
            axios({
                url: "/Data/deleteQualification",
                method: "POST",
                data: {
                    token: getCookie("token"),
                    params: {
                        studentID: studentID
                    }
                }
            }).then(function (rep) {
                if (rep.data.status === 'SUCCESS') {
                    setCookie("token", rep.data.token);
                    alert("取消免费资格成功");
                    window.location.href = "/admin_freeListManage.html"
                } else {
                    alert("取消免费资格失败" + rep.data.reason);
                }
            }, function () {
                alert("抱歉，服务器当前不可用");
            })

        },
        downloadSignUpList: function () {
            axios({
                url: "/Data/downLoadFinalList",
                method: "POST",
                data: {
                    token: getCookie("token")
                }
            }).then(function (rep) {
                if (rep.data.status === "SUCCESS") {
                    setCookie("token", rep.data.token);
                    let UUID = rep.data.UUID;
                    window.location.href = "/File/download?file=" + UUID;
                } else {
                    alert("报名信息获取失败" + rep.data.reason);
                }
            }, function () {
                alert("抱歉，服务器当前不可用");
            })
        }
    },
    created: function () {
        axios({
            url: "/Data/getApplyList",
            method: "POST",
            data: {
                token: getCookie("token")
            }
        }).then(function (rep) {
            if (rep.data.status === "SUCCESS") {
                setCookie("token", rep.data.token);
                app.students = rep.data.students;
            } else {
                alert("获取报名名单失败" + rep.data.reason);
            }
        }, function () {
            alert("抱歉，服务器当前不可用");
        })
    }
});