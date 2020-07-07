// 国外数据
var foreignData = null;
// 国内数据
var chinaData = null;
$.when($.ajax({
    url: "https://view.inews.qq.com/g2/getOnsInfo?name=disease_foreign",
    dataType: "jsonp",
    success: function (data) {
        foreignData = JSON.parse(data.data);
    }
}), $.ajax({
    url: "https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5",
    dataType: "jsonp",
    success: function (data) {
        chinaData = JSON.parse(data.data);
    }
})).then(function () {
    // 更新标题
    title();
    // 注入总览数据
    brief();
    // 世界疫情地图
    map();
    // 海外疫情趋势
    tendency();
    // 昨日新增确诊国家Top10
    top10();
})
// 标题
function title() {
    $(".brief .brief_header p").text("更新时间 - " + foreignData.globalStatis.lastUpdateTime)
}
// 注入总览数据
function brief() {
    // 拼接字符串
    var htmlStr = `
            <li class="allConfirm">
                <div class="number">${foreignData.globalStatis.confirm}</div>
                <div class="item">累计确诊</div>
                <div class="change"><span>昨日</span><b>+${foreignData.globalStatis.confirmAdd}</b></div>
            </li>
            <li class="nowConfirm">
                <div class="number">${foreignData.globalStatis.nowConfirm}</div>
                <div class="item">现有确诊</div>
                <div class="change"><span>昨日</span><b>+${foreignData.globalStatis.nowConfirmAdd}</b></div>
            </li>
            <li class="deadNum">
                <div class="number">${foreignData.globalStatis.dead}</div>
                <div class="item">死亡人数</div>
                <div class="change"><span>昨日</span><b>+${foreignData.globalStatis.deadAdd}</b></div>
            </li>
            <li class="cureNum">
                <div class="number">${foreignData.globalStatis.heal}</div>
                <div class="item">治愈人数</div>
                <div class="czhange"><span>昨日</span><b>+${foreignData.globalStatis.healAdd}</b></div>
            </li>
        `;
    // 设置字符串为HTML内容
    $(".brief_body").html(htmlStr);
}
// 世界疫情地图
function map() {
    // 创建变量保存目标数据
    var virusDatas = [];
    // 遍历数据，获取目标信息
    $.each(foreignData.foreignList, function (i, v) {
        virusDatas[i] = {};
        virusDatas[i].name = v.name;
        virusDatas[i].value = v.confirm;
    })
    // 加入中国数据插入到最后位置
    virusDatas.push({
        name: "中国",
        value: chinaData.chinaTotal.confirm
    });
    // 绘制图表
    // 1、初始化echarts实例
    var myChart = echarts.init(document.querySelector(".brief .map_info"));
    // 2、设置配置项
    var option = {
        // 设置提示信息
        tooltip: {
            // 设置提示信息触发源
            trigger: 'item',
            // 设置提示信息格式
            formatter: function (params) {
                return params.name + " : " + (params.value ? params.value : 0);
            }
        },
        // 视觉映射组件
        visualMap: {
            // 设置映射类型：piecewise分段型、continuous连续性
            type: 'piecewise',
            pieces: [
                { max: 0, label: '0', color: '#eee' },
                { min: 1, max: 499, label: '1-499', color: '#fff7ba' },
                { min: 500, max: 4999, label: '500-4999', color: '#ffc24b' },
                { min: 5000, max: 9999, label: '5000-9999', color: '#ff7c20' },
                { min: 10000, max: 100000, label: '1万-10万', color: '#fe5e3b' },
                { min: 100000, max: 500000, label: '10万-50万', color: '#e2482b' },
                { min: 500000, label: '50万以上', color: '#b93e26' },
            ],
            itemHeight: 10,
            itemWidth: 10,
            inverse: true,
        },
        // 系列列表
        series: [{
            // 数据名称
            name: '',
            // 设置数据
            data: virusDatas,
            // 绘制的图表类型
            type: 'map',
            // 指定地图名称
            mapType: 'world',
            // 地区名称映射
            nameMap: nameMap,
            // 图表所绘制区域样式
            itemStyle: {
                emphasis: {
                    areaColor: '#c9ffff',
                    label: {
                        show: false
                    }
                }
            },
            // 设置位置：保持地图高宽比的情况下把地图放在容器的正中间
            layoutCenter: ['center', 'center'],
            // 地图缩放
            layoutSize: "180%",
        }]
    };
    myChart.setOption(option);
    $(".brief .map_tab span").eq(0).click(function () { fn("confirm") });
    $(".brief .map_tab span").eq(1).click(function () { fn("nowConfirm") });
    function fn(valueName) {
        $.each(foreignData.foreignList, function (i, v) {
            virusDatas[i].value = v[valueName];
        })
        virusDatas[virusDatas.length - 1].value = chinaData.chinaTotal[valueName];
        option.series[0].data = virusDatas;
        myChart.setOption(option);
    }
    $(".map_tab span").click(function () {
        $(this).addClass("cur").siblings().removeClass("cur");
    })
}
