let formData = new window.FormData();
const app = new Vue({
    el: "#upload",
    data: {
        formData: formData
    },
    methods: {
        updata() {
            this.formData.append('userfile', document.getElementById('file').files[0]);
            this.formData.append('token', getCookie("token"));
            axios({
                url: '/File/upload',
                method: 'POST',
                data: formData
            }).then(function (rep) {
                if (rep.data.stauts === "success") {
                    setCookie("token", rep.data.token);
                    alert("上传成功");
                } else {
                    alert("上传失败" + rep.data.reason);
                }
            })
        }
    }
});