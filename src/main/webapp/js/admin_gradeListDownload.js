const gradeChartContainer1 = $("#gradeChartContainer1");
const gradeChartContainer2 = $("#gradeChartContainer2");
const gradeChart1 = echarts.init(document.getElementById("gradeChart1"));
const gradeChart2 = echarts.init(document.getElementById("gradeChart2"));
let startExamId;
let endExamId;

$(showScoreDistribution());

function showScoreDistribution() {
    $("#showScoreDistribution").addClass("active");
    $("#showAverageScore").removeClass("active");
    gradeChartContainer1.show();
    gradeChartContainer2.hide();
    gradeChart1.showLoading();
    gradeChart1.hideLoading();
    gradeChart1.setOption({
        title: {
            text: 'CCF',
            subtext: '分数分布',
            // x 设置水平安放位置，默认左对齐，可选值：'center' ¦ 'left' ¦ 'right' ¦ {number}（x坐标，单位px）
            x: 'left',
            // y 设置垂直安放位置，默认全图顶端，可选值：'top' ¦ 'bottom' ¦ 'center' ¦ {number}（y坐标，单位px）
            y: 'top',
            // itemGap设置主副标题纵向间隔，单位px，默认为10，
            itemGap: 30,
            backgroundColor: '#EEE',
            // 主标题文本样式设置
            textStyle: {
                fontSize: 26,
                fontWeight: 'bolder',
                color: '#107180'
            },
            // 副标题文本样式设置
            subtextStyle: {
                fontSize: 18,
                color: '#8B4354'
            }
        },
        series: [
            {
                name: '分数分布',
                type: 'pie',    // 设置图表类型为饼图
                radius: '55%',  // 饼图的半径，外半径为可视区尺寸（容器高宽中较小一项）的 55% 长度。
                data: [          // 数据数组，name 为数据项名称，value 为数据项值
                    {value: 235, name: '0-99'},
                    {value: 274, name: '100-199'},
                    {value: 310, name: '200-299'},
                    {value: 335, name: '300-399'},
                    {value: 400, name: '400-500'}
                ]
            }
        ],
        tooltip: {
            // trigger 设置触发类型，默认数据触发，可选值：'item' ¦ 'axis'
            trigger: 'item',
            showDelay: 20,   // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
            hideDelay: 20,   // 隐藏延迟，单位ms
            backgroundColor: '#FFC396',  // 提示框背景颜色
            textStyle: {
                fontSize: '16px',
                color: '#000'  // 设置文本颜色 默认#FFF
            },
            // formatter设置提示框显示内容
            // {a}指series.name  {b}指series.data的name
            // {c}指series.data的value  {d}%指这一部分占总数的百分比
            formatter: '{a} <br/>{b} : {c}个 ({d}%)'
        },
    });
}

function showAverageScore() {
    gradeChartContainer1.hide();
    gradeChartContainer2.show();
    $("#showScoreDistribution").removeClass("active");
    $("#showAverageScore").addClass("active");
}

function getAverageScore() {
    startExamId = $("#startExamId").val();
    endExamId = $("#endExamId").val();
    if (startExamId === '' || endExamId === '') {
        alert("请输入信息");
    } else {
        let xmlhttp;
        if (window.XMLHttpRequest) {
            // code for IE7+, Firefox, Chrome, Opera, Safari
            xmlhttp = new XMLHttpRequest();
        } else {
            // code for IE6, IE5
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlhttp.open("POST", "/Data/getGradeInfo");
        xmlhttp.setRequestHeader("Content-type", "application/json");
        let data = {
            token: getCookie("token"),
            params: {
                startExamId: startExamId,
                endExamId: endExamId
            }
        };
        xmlhttp.send(JSON.stringify(data));
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
                var responseJson = JSON.parse(xmlhttp.responseText);
                setCookie("token", responseJson.token);
                let sessions = [];
                for (let i = Number(startExamId); i < Number(endExamId); i++) {
                    sessions.push(String(i));
                }
                let sessionsGrade = responseJson.sessionsGrade;
                let option = {
                    backgroundColor: '#FFFDF8',
                    title: {
                        text: '历届平均分',
                        subtext: '',
                        x: 'center'
                    },
                    legend: {
                        // orient 设置布局方式，默认水平布局，可选值：'horizontal'（水平） ¦ 'vertical'（垂直）
                        orient: 'horizontal',
                        // x 设置水平安放位置，默认全图居中，可选值：'center' ¦ 'left' ¦ 'right' ¦ {number}（x坐标，单位px）
                        x: 'left',
                        // y 设置垂直安放位置，默认全图顶端，可选值：'top' ¦ 'bottom' ¦ 'center' ¦ {number}（y坐标，单位px）
                        y: 'top',
                        data: ['分数']
                    },
                    //  图表距边框的距离,可选值：'百分比'¦ {number}（单位px）
                    grid: {
                        top: '16%',   // 等价于 y: '16%'
                        left: '3%',
                        right: '8%',
                        bottom: '3%',
                        containLabel: true
                    },
                    // 提示框
                    tooltip: {
                        trigger: 'axis'
                    },
                    //工具框，可以选择
                    toolbox: {
                        feature: {
                            saveAsImage: {} //下载工具
                        }
                    },
                    xAxis: {
                        name: '届数',
                        type: 'category',
                        axisLine: {
                            lineStyle: {
                                // 设置x轴颜色
                                color: '#87CEFA'
                            }
                        },
                        // 设置X轴数据旋转倾斜
                        axisLabel: {
                            rotate: 30, // 旋转角度
                            interval: 0  //设置X轴数据间隔几个显示一个，为0表示都显示
                        },
                        // boundaryGap值为false的时候，折线第一个点在y轴上
                        boundaryGap: false,
                        data: sessions
                    },
                    yAxis: {
                        name: '分数',
                        type: 'value',
                        min: 0, // 设置y轴刻度的最小值
                        max: 500,  // 设置y轴刻度的最大值
                        splitNumber: 5,  // 设置y轴刻度间隔个数
                        axisLine: {
                            lineStyle: {
                                // 设置y轴颜色
                                color: '#87CEFA'
                            }
                        },
                    },
                    series: [
                        {
                            name: '分数',
                            data: sessionsGrade,
                            type: 'line',
                            // 设置小圆点消失
                            // 注意：设置symbol: 'none'以后，拐点不存在了，设置拐点上显示数值无效
                            symbol: 'none',
                            // 设置折线弧度，取值：0-1之间
                            smooth: 0.5,
                        }
                    ],
                    color: ['#000000']
                };
                gradeChart2.setOption(option);
            }
        };
    }
}




