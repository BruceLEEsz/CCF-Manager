let formData = new window.FormData();
const app = new Vue({
    el: "#upload",
    data: {
        formData: formData
    },
    methods: {
        upload() {
            this.formData.append('userfile', document.getElementById("sMessage").files[0]);
            this.formData.append("token",getCookie("token"));
            axios({
                url: "/File/upload",
                method: "POST",
                data: formData
            }).then(function (rep) {
                    if (rep.data.status === 'SUCCESS') {
                        setCookie("token", rep.data.token);
                        let UUID = rep.data.UUID;
                        axios({
                            url: "/Data/uploadStudentInfo",
                            method: "POST",
                            data: {
                                token: getCookie("token"),
                                params: {
                                    UUID: UUID
                                }
                            }
                        }).then(function (rep) {
                            if (rep.data.status === "SUCCESS") {
                                setCookie("token", rep.data.token);
                                alert("学生信息上传成功");
                            } else {
                                alert("学生信息上传失败" + rep.data.reason);
                            }
                        }, function () {
                            alert("抱歉，服务器当前不可用");
                        })
                    } else {
                        alert("学生信息上传失败" + rep.data.reason);
                    }
                }, function () {
                    alert("抱歉，服务器当前不可用");
                }
            )
        }
    }
});