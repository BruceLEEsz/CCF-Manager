const app = new Vue({
    el: "#freeListTable",
    data: {
        students: [
            {
                name:"LLL",
                studentID:"917116150216",
                confirm:false
            }
        ],
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
                if (rep.status === "SUCCESS") {
                    setCookie("token", rep.data.token);
                    alert("免费资格设置成功")
                } else {
                    alert("免费资格设置失败" + rep.data.reason);
                }
            }, function () {
                alert("抱歉，服务器当前不可用");
            })
        }
    },
    created: function () {
        axios({
            url: "/Data/getFreeList",
            method: "POST",
            data: {
                token: getCookie("token")
            }
        }).then(function (rep) {
            if (rep.status === "SUCCESS") {
                setCookie("token", rep.data.token);
                this.students = rep.data.students;
            } else {
                alert("获取报名名单失败" + rep.data.reason);
            }
        }, function () {
            alert("抱歉，服务器当前不可用");
        })
    }
});