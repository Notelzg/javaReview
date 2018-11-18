/**
 * ��ȡ��Ҫ������
 * @param keyArr ��Ҫ�õ���key���飬����Ǹ���ÿ��ģ�����õĳ�������
 * @param keyPropertyArr   key��һ��json�ṹ key = {"value": "1", ...}
 * @param resultMap ���ݼ�
 * @param mapPropertyArr  resultMap �а�����map����������
 * @param operation  "����������ݣ���Ҫ���еĲ�����Ŀǰֻ֧��+ - * / ����
 * @returns {Array} ����key�����ԣ�������Ӧkey���ԵĶ�ά���顣
 */
function getDate1(keyArr, keyPropertyArr, resultMap, mapPropertyArr, operation) {
    var valueArray = getValueArray3(keyArr, keyPropertyArr, resultMap, mapPropertyArr);
    valueArray.push(getValueArrayOperation(valueArray[1], valueArray[3], operation))
    return valueArray;
};

function getDate2(keyArr, keyPropertyArr1, keyPropertyArr2, operation, data, mapPropertyArr) {
    //����=����-����
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
    /* ���������ݽ��У���ʽ�� */
    valueArray[0] = arrayValueFormat(valueArray[0], "��", 2);
    for (var i = 1; i < valueArray.length; i++) {
        valueArray[i] = arrayValueFormat(valueArray[i], "", 2);
    }
    var domDiv = document.getElementById(divName);
    var colorArr = ['#20b2aa', '#ffa07a', '#ff0000', '#ffff00'];//ͼ������ɫ���ֱ��Ӧ[ʵ��ֵ��Ԥ��ֵ������ֵ]
    var titleArray = ['ʵ��ֵ', 'ԭֵ', '�޸�ֵ', '����'];
    echartsStackedColumn(domDiv, titleArray, colorArr, yAxisName, valueArray);
};

/**
 *  ���̱��棬 ͼ���ʼ��
 * @param data ���ݼ���
 */
function echartsDrawModel_1(data) {
    var keyProperty = ["year", "value", "value", "value"];
    var mapProperty = ["replayMap", "outputMap", "outputMap", "replayMap"];
    var valueArray = getDate1(DifConstants.businessCellArray, keyProperty, data, mapProperty,"-");
    echartsInit(valueArray, "Ӫҵ����", "container_com");

    valueArray = getDate1(DifConstants.profitCellArray, keyProperty, data, mapProperty, "-");
    echartsInit(valueArray, "������", "echarts_profit");

    valueArray = getDate1(DifConstants.managementCellArray, keyProperty, data, mapProperty, "-");
    echartsInit(valueArray, "��Ӫ��ֽ�����", "echarts_inflow");
    var keyProperty1 = ["year", "value1", "value1", "value1"];
    var keyProperty2 = ["year", "value2", "value2", "value2"];

    valueArray = getDate2(DifConstants.netamountCellArray,  keyProperty1, keyProperty2,"-" , data, mapProperty);
    echartsInit(valueArray, "��Ӫ��ֽ�������", "echarts_amount");
}