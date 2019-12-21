const gradeChartContainer1 = $("#gradeChartContainer1");
const gradeChartContainer2 = $("#gradeChartContainer2");
const gradeChart1 = echarts.init(document.getElementById("gradeChart1"));
const gradeChart2 = echarts.init(document.getElementById("gradeChart2"));
const ul = {"0": $("#0"), "1": $("#1"), "2": $("#2"), "3": $("#3"), "4": $("#4"), "5": $("#5")};
const ul2 = {"0": $("#00"), "1": $("#11"), "2": $("#22"), "3": $("#33"), "4": $("#44"), "5": $("#55")};
$(chooseIndex1('0'));

function showScoreDistribution(index) {
    $("#showScoreDistribution").addClass("active");
    $("#showAverageScore").removeClass("active");
    gradeChartContainer1.show();
    gradeChartContainer2.hide();
    $("#nav1").show();
    $("#nav2").hide();
    gradeChart1.showLoading();
    let redata;
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", "/Data/scoreInfo", true);
    xmlhttp.setRequestHeader("Content-type", "application/json");
    let data = {
        token: getCookie("token"),
        params: {
            type: "interval",
            index: index
        }
    };
    xmlhttp.send(JSON.stringify(data));
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            let repJson = JSON.parse(xmlhttp.responseText);
            setCookie("token", repJson.token);
            redata = repJson.result;
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
                        data: redata
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
            }, 'json');
            gradeChart1.hideLoading();
        }
    };
}

function showAverageScore() {
    gradeChartContainer1.hide();
    gradeChartContainer2.show();
    $("#nav1").hide();
    $("#nav2").show();
    $("#showScoreDistribution").removeClass("active");
    $("#showAverageScore").addClass("active");
    chooseIndex2('0');
}

function getAverageScore(index) {
    let XData = ['均分', '中位数', '方差'];
    let YData0 = [];
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", "/Data/scoreInfo", true);
    xmlhttp.setRequestHeader("Content-type", "application/json");
    let data = {
        token: getCookie("token"),
        params: {
            type: "score",
            index: index
        }
    };
    xmlhttp.send(JSON.stringify(data));
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            let reJson = JSON.parse(xmlhttp.responseText);
            setCookie("token", reJson.token);
            YData0.push(reJson.average);
            YData0.push(reJson.median);
            YData0.push(reJson.variance);
            let option = {
                /* 柱状图颜色 */
                color: ['#AFBBE3'],
                /* 四周边距(单位默认px，可以使用百分比) */
                grid: {
                    left: 40,
                    top: 30,
                    right: 50,
                    bottom: 30
                },
                /* 鼠标悬浮显示数据 */
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                        type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                toolbox: {
                    show: true
                },
                calculable: true,
                xAxis: [
                    {
                        type: 'category',
                        //设置轴线的属性
                        axisLine: {
                            lineStyle: {
                                color: '#6ab2ec',
                            }
                        },
                        data: XData,
                    }
                ],
                yAxis: [
                    {
                        type: 'value',
                        // 控制网格线是否显示
                        splitLine: {
                            show: true,
                            //  改变轴线颜色
                            lineStyle: {
                                // 使用深浅的间隔色
                                color: ['#132a6e']
                            }
                        },
                        //设置轴线的属性
                        axisLine: {
                            lineStyle: {
                                color: '#6ab2ec',
                            }
                        }
                    }
                ],
                series: [
                    {
                        name: '总分',
                        type: 'bar',
                        /* 柱子的显示宽度 */
                        barWidth: '20%',
                        data: YData0,
                        /* 显示柱子数据 */
                        label: {
                            normal: {
                                show: true,
                                // 数据在柱子头部显示
                                position: 'top',
                                textStyle: {
                                    color: '#5475c7',
                                    fontSize: 16,
                                }
                            }
                        }
                    }
                ]
            };
            gradeChart2.setOption(option);
        }
    }

}

function chooseIndex1(index) {
    for (let index in ul) {
        ul[index].removeClass("active");
    }
    ul[index].addClass("active");
    showScoreDistribution(index);
}

function chooseIndex2(index) {
    for (let index in ul2) {
        ul2[index].removeClass("active");
    }
    ul2[index].addClass("active");
    getAverageScore(index);
}