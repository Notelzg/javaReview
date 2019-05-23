/**
 * liuqian2018-10-16
 * 
 * 由于在模型计算结果数据model_output表中没有维护数据单位，在经评报告页面中获取的数值需要从页面中获取其单位并对cellValue进行格式化处理
 * 参数：
 * cellValue：从model_out表中获取到的计算值
 * tdUnit：页面中当前cellValue的单位
 * precision：小数点精度
 * @param defaultValue 默认值
 * */
function cellValueFormat(cellValue,tdUnit,precision, defaultValue){
    // precision = 5;
    cellValue = cellValue + "";//将cellValue强转为字符串类型
    if (typeof (cellValue) == "undefined" || cellValue == "undefined" || cellValue == "NaN" ||  cellValue=="") {
        cellValue = defaultValue == null ?"" : defaultValue;
    } else  if(cellValue == "0" || isNaN(cellValue)) {
        //不是number，不进行任何处理
	}else{
		//var tdUnit = $(tdArr[i]).next("td").text();
		cellValue = parseFloat(cellValue);//string转换number，以便调用toFixed精度函数
		//cellValue的单位如果是百分比则进行处理：cellValue = cellValue * 100
		if("%" == tdUnit){
			cellValue = cellValue * 100;
			cellValue = cellValue.toFixed(precision);
			cellValue = f(cellValue, precision);//百分比精度
            cellValue += "%";
			console.log("百分比数据处理完成：cellValue = "+cellValue);
		}else if("年" == tdUnit) {//由于model_out表中年份是double类型的，由形参传入年份的精度，比如在Echarts的X轴则设置年份的精度为0
            cellValue = cellValue.toFixed(precision);
            cellValue = f(cellValue, precision);//百分比精度
            console.log("年数据处理完成：cellValue = " + cellValue);
        }else if (""==tdUnit || null==tdUnit || typeof(tdUnit)=="undefined"){//不指定单位的情况
        	if(typeof(cellValue)=="number" || !isNaN(cellValue)){//cellValue是数字类型或者是符合数字类型的字符串
        		cellValue = f(cellValue, precision);
        	}
		}else{
            if(typeof(cellValue)=="number" || !isNaN(cellValue)){//cellValue是数字类型或者是符合数字类型的字符串
                cellValue = f(cellValue, precision);
            }
		}
	}
	return cellValue;
}

/**
 * @author zgli
 * 数组初始化
 */
function arrayValueFormat(cellArray,tdUnit,precision, defaultValue) {
    for (var i = 0; i < cellArray.length; i++){
        cellArray[i] = cellValueFormat(cellArray[i], tdUnit, precision,defaultValue);
    }
    return cellArray;
}

/**
 *  @author zgli
 *  添加对数字格式化，如果小数点后面有值，
 *  则按照精度处理，没有则去掉多余 0
 *  */
function f(cellValue, precision) {
    /* 添加对数字格式化，如果小数点后面有值，则按照精度处理，没有则去掉0*/
    var array = cellValue.toString().split(".");
    var index=-1;
    if(array.length>1){
        for(var i = 0;i<array[1].length && i<precision;i++) {
            if (array[1].charAt(i) != "0") {
                index=i+1;
            }
        }
    }
    if(index!=-1){
        cellValue = Number(cellValue).toFixed(precision);
    }else{
        cellValue = parseInt(cellValue);
    }
    return cellValue;
};

/**
 *  format number，support %
 * @param value  number
 * @param precision
 * @param unit %
 * @return 50%
 */
function valueFormat(value, precision, unit){
    // debugger;
    switch (unit) {
        case "%":
            value = cellValueFormat(value, "%", precision);
            break;
        default:
            value = cellValueFormat(value, unit, precision);
    }
    return value;

}
