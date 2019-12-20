function admin_timeSet() {
    let token = getCookie("token");
    if (token !== '') {
        let tokenArray = token.split('.');
        const decodeToken = Base64.decode(tokenArray[1]);
        let jsonToken = JSON.parse(decodeToken);
        if (jsonToken.userType !== "ADMIN") {
            alert("权限不足");
        } else {
            window.location.href = "/admin_timeSet.html";
        }
    } else {
        window.location.href = "/login.html";
        alert("请先登录");
    }
}

function admin_scoreLineSet() {
    let token = getCookie("token");
    if (token !== '') {
        let tokenArray = token.split('.');
        const decodeToken = Base64.decode(tokenArray[1]);
        let jsonToken = JSON.parse(decodeToken);
        if (jsonToken.userType !== "ADMIN") {
            alert("权限不足");
        } else {
            window.location.href = "/admin_scoreLineSet.html";
        }
    } else {
        window.location.href = "/login.html";
        alert("请先登录");
    }
}

function admin_freeListManage() {
    let token = getCookie("token");
    if (token !== '') {
        let tokenArray = token.split('.');
        const decodeToken = Base64.decode(tokenArray[1]);
        let jsonToken = JSON.parse(decodeToken);
        if (jsonToken.userType !== "ADMIN") {
            alert("权限不足");
        } else {
            window.location.href = "/admin_freeListManage.html";
        }
    } else {
        window.location.href = "/login.html";
        alert("请先登录");
    }
}

function admin_sMessageUpload() {
    let token = getCookie("token");
    if (token !== '') {
        let tokenArray = token.split('.');
        const decodeToken = Base64.decode(tokenArray[1]);
        let jsonToken = JSON.parse(decodeToken);
        if (jsonToken.userType !== "ADMIN") {
            alert("权限不足");
        } else {
            window.location.href = "/admin_sMessageUpload.html";
        }
    } else {
        window.location.href = "/login.html"
        alert("请先登录");
    }
}

function admin_sGradeUpload() {
    let token = getCookie("token");
    if (token !== '') {
        let tokenArray = token.split('.');
        const decodeToken = Base64.decode(tokenArray[1]);
        let jsonToken = JSON.parse(decodeToken);
        if (jsonToken.userType !== "ADMIN") {
            alert("权限不足");
        } else {
            window.location.href = "/admin_sGradeUpload.html";
        }
    } else {
        window.location.href = "/login.html";
        alert("请先登录");
    }
}

function admin_gradeListDownload() {
    let token = getCookie("token");
    if (token !== '') {
        let tokenArray = token.split('.');
        const decodeToken = Base64.decode(tokenArray[1]);
        let jsonToken = JSON.parse(decodeToken);
        if (jsonToken.userType !== "ADMIN") {
            alert("权限不足");
        } else {
            window.location.href = "/admin_gradeListDownload.html";
        }
    } else {
        window.location.href = "/login.html";
        alert("请先登录");
    }
}

function admin_setCode() {
    let token = getCookie("token");
    if (token !== '') {
        let tokenArray = token.split('.');
        const decodeToken = Base64.decode(tokenArray[1]);
        let jsonToken = JSON.parse(decodeToken);
        if (jsonToken.userType !== "ADMIN") {
            alert("权限不足");
        } else {
            window.location.href = "/admin_setCode.html";
        }
    } else {
        window.location.href = "/login.html";
        alert("请先登录");
    }
}

function logout() {
    setCookie("token", "");
    window.location.href = "/login.html";
}

function changePassword() {
    let token = getCookie("token");
    if (token !== '') {
        let tokenArray = token.split('.');
        const decodeToken = Base64.decode(tokenArray[1]);
        let jsonToken = JSON.parse(decodeToken);
        window.location.href = "/changePassword.html";
    } else {
        window.location.href = "/login.html";
        alert("请先登录");
    }

}