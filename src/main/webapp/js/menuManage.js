function admin_timeSet() {
    let token = getCookie("token");
    console.log(token);
    if (token !== '') {
        let tokenArray = token.split('.');
        const decodeToken = Base64.decode(tokenArray[1]);
        if (decodeToken.userType !== "ADMIN") {
            alert("权限不足");
        } else {
            window.location.href = "../webapp/admin_timeSet.html";
        }
    } else {
        window.location.href = "../webapp/login.html"
        alert("请先登录");
    }
}

function admin_scoreLineSet() {
    let token = getCookie("token");
    console.log(token);
    if (token !== '') {
        let tokenArray = token.split('.');
        const decodeToken = Base64.decode(tokenArray[1]);
        if (decodeToken.userType !== "ADMIN") {
            alert("权限不足");
        } else {
            window.location.href = "../webapp/admin_scoreLineSet.html";
        }
    } else {
        window.location.href = "../webapp/login.html"
        alert("请先登录");
    }
}

function admin_freeListManage() {
    let token = getCookie("token");
    console.log(token);
    if (token !== '') {
        let tokenArray = token.split('.');
        const decodeToken = Base64.decode(tokenArray[1]);
        if (decodeToken.userType !== "ADMIN") {
            alert("权限不足");
        } else {
            window.location.href = "../webapp/admin_freeListManage.html";
        }
    } else {
        window.location.href = "../webapp/login.html"
        alert("请先登录");
    }
}

function admin_sMessageUpload() {
    let token = getCookie("token");
    console.log(token);
    if (token !== '') {
        let tokenArray = token.split('.');
        const decodeToken = Base64.decode(tokenArray[1]);
        if (decodeToken.userType !== "ADMIN") {
            alert("权限不足");
        } else {
            window.location.href = "../webapp/admin_sMessageUpload.html";
        }
    } else {
        window.location.href = "../webapp/login.html"
        alert("请先登录");
    }
}

function admin_sGradeUpload() {
    let token = getCookie("token");
    console.log(token);
    if (token !== '') {
        let tokenArray = token.split('.');
        const decodeToken = Base64.decode(tokenArray[1]);
        if (decodeToken.userType !== "ADMIN") {
            alert("权限不足");
        } else {
            window.location.href = "../webapp/admin_sGradeUpload.html";
        }
    } else {
        window.location.href = "../webapp/login.html"
        alert("请先登录");
    }
}

function admin_gradeListDownload() {
    let token = getCookie("token");
    console.log(token);
    if (token !== '') {
        let tokenArray = token.split('.');
        const decodeToken = Base64.decode(tokenArray[1]);
        if (decodeToken.userType !== "ADMIN") {
            alert("权限不足");
        } else {
            window.location.href = "../webapp/admin_gradeListDownload.html";
        }
    } else {
        window.location.href = "../webapp/login.html";
        alert("请先登录");
    }
}

function admin_setCode() {
    let token = getCookie("token");
    console.log(token);
    if (token !== '') {
        let tokenArray = token.split('.');
        const decodeToken = Base64.decode(tokenArray[1]);
        if (decodeToken.userType !== "ADMIN") {
            alert("权限不足");
        } else {
            window.location.href = "../webapp/admin_setCode.html";
        }
    } else {
        window.location.href = "../webapp/login.html";
        alert("请先登录");
    }
}