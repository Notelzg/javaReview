/*** 自动生成echarts的option，主要是针对Y轴的生成做了，优化，
 * 根据Y轴的数据，自动生成Y轴的配置数组，每个形状的图形，做了一个
 * 目前支持，堆叠柱状图,折线图，堆叠面积图
 */
/**
 * @author zgli
 * 堆叠柱状图
 *@param divDom：图表元素的位置，DOM DIV元素
 *@param colorArr：图例的颜色
*/
/**
 *
 * @param divDom  图表元素的位置，DOM DIV元素
 * @param titleArray  图表元素的名称数组，按照图元素显示的顺序
 * @param colorArr 图表元素的颜色数组，按照图元素显示的顺序
 * @param yAxisName  该图表Y轴的名称，在图表的左上角显示
 * @param valueArray  这是一个数组（二维），第一个元素是年份数组
 *  最后一个元素是差异线条，其他的数据元素是柱状图的柱。
 */
function echartsStackedColumn(divDom, titleArray,colorArr,yAxisName,valueArray){
    /* 生成y轴的 serial 数组*/
    var ySeries = [] ;
    /* y轴数据的，柱状数据 */
    for(var i = 0; i < titleArray.length -1; i++){
        var temp = {"name":titleArray[i]
            ,"type":"bar"
            ,"label":{ "show": true, "position": "inside"}
            ,"data": valueArray[i + 1]
        }
        ySeries.push(temp);
    }
    /* 生成差异分析的线条, 数组最后一个元素 */
    var diffLine = {
        "name" :'差异',
        "type" :'line',
        
        "label" : { "normal": { "show": true, "position": 'top' } },
        "itemStyle" : { "normal" : { "lineStyle":{ "color":'#ff0000' } } },
        "data":valueArray[valueArray.length - 1]
    }
    ySeries.push(diffLine);
    /* y轴配置，结束  */
    /* 初始化 echarts 的  dom */
    var Dom = divDom;
    var myChart = echarts.init(Dom);

    /* echarts option */
    var app = {};
    option = null;
    app.title = '堆叠柱状图';

    option = {
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
            data: titleArray
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        color:colorArr,//通过形参传入图例的颜色
        xAxis : [
            {
                type : 'category',
                data : valueArray[0]
//            	data :['2018','2019','2020','2021','2022']
            }
        ],
        yAxis : [
            {
                type: 'value',
                //name: '经营现金流入',
                name: yAxisName,//通过形参传入该值
            },
            {
                type: 'value',
                name: '',
            }
        ],
        series : ySeries
        ,dataZoom: [{}],//初始化滚动条
    };
    ;
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
    console.log("差异分析Echarts图表初始化完成："+yAxisName);
};
///* 堆叠状图结束 */

/**
 * 堆叠面积图
 * @author zgli
 * @param divDom  图表元素的位置，DOM DIV元素
 * @param titleArray  图表元素的名称数组，按照图元素显示的顺序
 * @param colorArr 图表元素的颜色数组，按照图元素显示的顺序
 * @param yAxisName  该图表Y轴的名称，在图表的左上角显示
 * @param valueArray  这是一个数组（二维），第一个元素是年份数组
 *  最后一个元素是差异线条，其他的数据元素是面积图的面积。
 */
function echartsStackedArea(divDom, titleArray,colorArr,yAxisName,valueArray){
    /* 生成y轴的 serial 数组*/
    var ySeries = [] ;
    /* y轴数据的，柱状数据 */
    for(var i = 0; i < titleArray.length - 1; i++){
        var temp =  {
            "name" : titleArray[i],
            "type" :'line',
           
            "areaStyle" : {"normal": {}},
            "data" : valueArray[i + 1]
        };
        ySeries.push(temp);
    }
    /* 生成差异分析的线条, 数组最后一个元素 */
   var finalLine = {
        "name" :'净利润',
        "type" :'line',
        
        "label" : { "normal" : { "show" : true, "position" : 'top' } },
        "areaStyle": { "normal": {}},
        "data" : valueArray[valueArray.length - 1]
    };
   ySeries.push(finalLine);
   /* 初始化 y 轴 完成  */

    var myCharscost = echarts.init(divDom);
    option = {
        title: {
            text: yAxisName,
            x:'center'
        },
        tooltip : {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                label: {
                    backgroundColor: '#6a7985'
                }
            }
        },
        legend: {
            // data:['付现成本','折旧摊销','利息费用','所得税','净利润']
            data: titleArray,
            x:'center',
            padding:40,
            itemGap: 5,//图例间距
            //y:'bottom'
        },
        color: colorArr,
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                //data : ['2019','2020','2021','2022','2023','2024','2025']
                data :  valueArray[0]
            }
        ],
        yAxis : [
            {
                type : 'value',
                // name: '成本构成表',
                //name: yAxisName,
            }
        ],
        series : ySeries,
        dataZoom: [{}],
    };
    myCharscost.setOption(option);
};
///*成本构成表结束*/

/**
 * 折线图
 * @author zgli
 * @param divDom  图表元素的位置，DOM DIV元素
 * @param titleArray  图表元素的名称数组，按照图元素显示的顺序
 * @param colorArr 图表元素的颜色数组，按照图元素显示的顺序
 * @param yAxisName  该图表Y轴的名称，在图表的左上角显示
 * @param valueArray  这是一个数组（二维），第一个元素是年份数组
 *  最后一个元素是差异线条，其他的数据元素是面积图的面积。
 */
function echartsLine(divDom, titleArray,colorArr,yAxisName,valueArray){
    /* 生成y轴的 serial 数组*/
    var ySeries = [] ;
    /* y轴数据的，柱状数据 */
    for(var i = 0; i < titleArray.length; i++){
        var temp = {
            "name" :  titleArray[i],
            "type" : 'line',
            "data" : valueArray[i + 1]
        };
        ySeries.push(temp);
    }; /* 初始化 y 轴 完成  */
        var myCharloan = echarts.init(divDom);
        option = {
            title: {
                text: yAxisName,
                x:'center'
            },
            // color: ['#D53A35', '#E98F6F', '#6AB0B8', '#334B5C'],
            color: colorArr,
            // title: {
            //    text:
            // },
            tooltip: {
                trigger: 'axis',
                //formatter: "{b} <br> 合格率: {c}%"
            },
            legend: {
                data: titleArray,
                x:'center',
                padding:40
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },
            xAxis: {
                type: 'category',
                name: '日期',
                boundaryGap: false,
                data: valueArray[0]
                //data: ['5.1', '5.2', '5.3', '5.4', '5.5', '5.6', '5.7']
            },
            yAxis: {
                type: 'value',
                //name: yAxisName,
            },
            series: ySeries,
            dataZoom: [{}]
        };
        myCharloan.setOption(option);
};
//结束

/**
 * 饼状图
 * @param divDom
 * @param title 标题
 * @param value1
 * @param value2
 * @param value3
 * @param conditionArray  判断条件，显示的结果，
 * @param  colorArr  颜色数组
 * example :
 * if (value1 >= value2){
 *      发电水平高于全省平均，适宜开发
 * }else{
 *      if (value1 >= value2- value3){
 *           风险适中，可以考虑开发
 *      }else{
 *           风险稍高，谨慎开发
 *      }
 * }
 */
function  echartsPieChartOption(value1, value2, value3, conditionArray,divDom, title, colorArr) {
    /* 饼图的范围*/
    max = Math.max(value1, value2) + 1000;
    min = Math.min(0, value1);
    var dom = divDom;
    var myChart = echarts.init(dom);
    var app = {};
    option = null;
    option = {
        /*backgroundColor: new echarts.graphic.RadialGradient(0.3, 0.3, 0.8, [{
            offset: 0,
            color: '#f7f8fa'
        }, {
            offset: 1,
            color: '#cdd0d5'
        }]),*/
        title: {
            // text: '发电量风险分析',
            text: title,
            left: 'center'
        },
        tooltip: {
            formatter: "{a} <br/>{b} : {c}"
        },
        series: [{
            type: "gauge",
            startAngle: 190,
            endAngle: -10,
            min: min,
            max: max,
            splitNumber: 3,
            radius: "90%",
            center: ["50%", "65%"],
            axisLine: {
                show: true,
                lineStyle: {
                    width: 100,
                    shadowBlur: 10,
                    // color: [ [0, '#B5495B'], [0.33, '#D0104C'], [0.66, '#f09426'], [1, '#20604F'] ]
                    color: colorArr
                }
            },
            axisTick: {
                show: false
            },
            axisLabel: {
                formatter: function (e) {
                    switch (e + "") {
                        case "500": return "风险稍高，请谨慎开发";
                        case "1000": return "风险稍高，请谨慎开发";
                        case "2300": return "风险适中，可考虑开发";
                        case "3000": return "发电量高于全省水平适应开发";
                        case "3000": return "发电量高于全省水平适应开发";
                        case "3000": return "发电量高于全省水平适应开发";
                        default: return "e3"
                    }
                },
                textStyle: {
                    color: "#fff",
                    fontSize: 12,
                    fontWeight: "bolder",
                    left: 10
                }
            },
            splitLine: {
                show: false
            },
            pointer: {
                width: "3%",
                length: '90%',
                color: "black"
            },
            itemStyle: {
                normal: {
                    color: "rgba(255, 255, 255, 0.8)",
                    shadowBlur: 20
                }
            },
            title: {
                show: false
            },
            detail: {
                show: true
            },
            data: [{
                value: value1,
                // name: '发电量风险分析'
                name: title
            }]
        }]
    }
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
};
