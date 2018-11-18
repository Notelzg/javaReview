/**
 * 获取需要的数据
 * @param keyArr 需要用到的key数组，这个是根据每个模型配置的常量数组
 * @param keyPropertyArr   key是一个json结构 key = {"value": "1", ...}
 * @param resultMap 数据集
 * @param mapPropertyArr  resultMap 中包含子map，属性数组
 * @param operation  "差异分析数据，需要进行的操作：目前只支持+ - * / 操作
 * @returns {Array} 根据key的属性，返回相应key属性的二维数组。
 */
function getDate1(keyArr, keyPropertyArr, resultMap, mapPropertyArr, operation) {
    var valueArray = getValueArray3(keyArr, keyPropertyArr, resultMap, mapPropertyArr);
    valueArray.push(getValueArrayOperation(valueArray[1], valueArray[3], operation))
    return valueArray;
};

function getDate2(keyArr, keyPropertyArr1, keyPropertyArr2, operation, data, mapPropertyArr) {
    //净额=流出-流入
    var valueArray1 = getValueArray3(DifConstants.netamountCellArray, keyPropertyArr1, data, mapPropertyArr);
    var valueArray2 = getValueArray3(DifConstants.netamountCellArray, keyPropertyArr2, data, mapPropertyArr);
    var valueArray = [valueArray1[0]];
    for (var i = 1; i < valueArray2.length; i++) {
        valueArray.push(getValueArrayOperation(valueArray2[i], valueArray1[i], operation));
    }
    valueArray.push(getValueArrayOperation(valueArray[1], valueArray[3], "-"))
    return valueArray;
};

function echartsInit(valueArray, yAxisName, divName) {
    /* 对数组数据进行，格式化 */
    valueArray[0] = arrayValueFormat(valueArray[0], "年", 2);
    for (var i = 1; i < valueArray.length; i++) {
        valueArray[i] = arrayValueFormat(valueArray[i], "", 2);
    }
    var domDiv = document.getElementById(divName);
    var colorArr = ['#20b2aa', '#ffa07a', '#ff0000', '#ffff00'];//图例的颜色，分别对应[实际值，预测值，差异值]
    var titleArray = ['实际值', '原值', '修改值', '差异'];
    echartsStackedColumn(domDiv, titleArray, colorArr, yAxisName, valueArray);
};

/**
 *  复盘报告， 图表初始化
 * @param data 数据集合
 */
function echartsDrawModel_1(data) {
    var keyProperty = ["year", "value", "value", "value"];
    var mapProperty = ["replayMap", "outputMap", "outputMap", "replayMap"];
    var valueArray = getDate1(DifConstants.businessCellArray, keyProperty, data, mapProperty,"-");
    echartsInit(valueArray, "营业收入", "container_com");

    valueArray = getDate1(DifConstants.profitCellArray, keyProperty, data, mapProperty, "-");
    echartsInit(valueArray, "净利润", "echarts_profit");

    valueArray = getDate1(DifConstants.managementCellArray, keyProperty, data, mapProperty, "-");
    echartsInit(valueArray, "经营活动现金流入", "echarts_inflow");
    var keyProperty1 = ["year", "value1", "value1", "value1"];
    var keyProperty2 = ["year", "value2", "value2", "value2"];

    valueArray = getDate2(DifConstants.netamountCellArray,  keyProperty1, keyProperty2,"-" , data, mapProperty);
    echartsInit(valueArray, "经营活动现金流净额", "echarts_amount");
}