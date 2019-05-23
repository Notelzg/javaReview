/**
 * 通过keyArray,获取valueMap中相应key的value
 * @param keyArray  由key = {"year": "2018, "value" : "10"}  组成数组
 * @param valueMap  values 的 map集合
 * @param property  key的属性，year 或者 value
 * @returns {Array}  由keyArray
 */
function getValueArray(keyArray, valueMap, property){
    var valueArray = [];
    for(var i=0;i<keyArray.length;i++){
        var key = keyArray[i];
        var value = valueMap[key[property]];//净利润，原值
        valueArray.push(value);
    }
    return valueArray;
}

/**
 * @param keyArray key的数组，key是一个json数据结构 suchas key = {"year": "2018, "value" : "10" , ...}
 * @param valueMap  所有数据都在一个map中。
 * @param propertyArray key的属性数组
 * @returns {Array} 二维数组，第i行代表propertyArray[i[属性的所有数据，
 */
function getValueArray2(keyArray, propertyArray, valueMap){
    var valueArray = [];
    for(var i=0;i<propertyArray.length;i++){
        var childArray = [];
        for (var j = 0; j < keyArray.length; j ++) {
            var key = keyArray[j];
            var value = valueMap[key[propertyArray[i]]];//净利润，原值
            childArray.push(value);
        }
        valueArray.push(childArray);
    }
    return valueArray;
}

/**
 * 一个map由几个childMap组成，childMap的key一样，需要从每个childMap取出相同的key组成一个数组
 * @param keyArray key的数组，keykey是一个json数据结构 suchas key = {"year": "2018, "value" : "10" , ...}
 * @param valueMap  所有数据都在一个map中。
 * @param keyPropertyArray key的属性数组 ["value1", "value2", ...]
 * @param mapPropertyArray map的属性数组,map中包含map的情况
 * such as : map = {"map0": {"value1": "1", "value2": "2"}, "map1": {}, "map2": {}, ...}
  * @returns {Array} 二维数组，第i行代表, mapPropertyArray[i]中,
 * keyArryay数组所有key的propertyArray[i[属性的数据，
 * 注意：keyPropertyArray 和 mapPropertyArray 是一一对应关系
 */
function getValueArray3(keyArray, keyPropertyArray, valueMap,mapPropertyArray){
    if (keyPropertyArray.length != mapPropertyArray.length){
         console.error("keyPropertyArray ， mapPropertyArray length is not equal"+ keyPropertyArray + mapPropertyArray);
         return;
    }
    var valueArray = [];
    for(var i=0;i<keyPropertyArray.length;i++){
        var childArray = [];
        for (var j = 0; j < keyArray.length; j ++) {
            var key = keyArray[j][keyPropertyArray[i]];
            var value = valueMap[mapPropertyArray[i]][key];
            childArray.push(value);
        }
        valueArray.push(childArray);
    }
    return valueArray;
}

/**
 * 两个数组进行运算， v = v1 operation v1
 * @param valueArray1   array of v1
 * @param valueArray2   array of v2
 * @param operation  type String : + - * /
 * @returns {Array} array of v
 */
function getValueArrayOperation(valueArray1, valueArray2, operation){
    var valueArray = [];
    for(var i=0;i<Math.max(valueArray1.length, valueArray2.length);i++){
        var v1 =  valueArray1[i];
        var v2 =  valueArray2[i];
        switch (operation) {
            case "+":
                valueArray.push(v1 + v2);
                break;
            case "-":
                valueArray.push(v1 - v2);
                break;
            case "*":
                valueArray.push(v1 * v2);
                break;
            case "/":
                valueArray.push(v1 / v2);
                break;
        }
    }
    return valueArray;
};

/**
 *  value是一个json数据结构 suchas value = {"year": "2018, "value" : "10" , ...}
 * @param valueArray  由map of value 组成的数组
 * @param propertyArray value的属性数组
 * @returns {Array} 二维数组，第i行代表propertyArray[i[属性的所有数据，
 */
function getValueArrayTArray(propertyArray, valueArray){
    var rs = [];
    for(var i=0;i<propertyArray.length;i++){
        var childArray = [];
        for (var j = 0; j < valueArray.length; j ++) {
            var value = valueArray[j];
            var key = propertyArray[i];
            childArray.push(value[key]);
        }
        rs.push(childArray);
    }
    return rs;
};

/**
 *  遍历conditionArray判断valueArry中index下标的数组，如果相等则取出propertyArray中的值，
 *  返回一个新的数组，数组长度是codnitionArray的长度
 * @param conditionArray  条件数组，目前只能支持相等判断
 * @param propertyArray propertyArray value的属性数组
 * @param valueArray   由value组成的数组,value是一个json数据结构 suchas value = {"year": "2018, "value" : "10" , ...}
 * @param index  valueArray 中作为判断条件的索引，从0开始，如果为null则设置为0
 */
// var pa = ["year", "value"];
// var va = [{"year":"2016", "value":"1992"}, {"year":"2017", "value":"1993"}, {"year":"2019", "value":"1994"}];
// var ca = ["2016", "2017", "2018", "2019", "2020"];
// getValueArrayCondition(pa, va, ca, null);
function getValueArrayCondition(propertyArray, valueArray, conditionArray, index) {
    var rs = [];
   valueArray = getValueArrayTArray(propertyArray, valueArray) ;
   index = index == null? 0 : index;
   var indexCondition = null;
   var indexValue = null;
   var valueCondition = null;
   var valueValue = null;
    for(var i=0;i<propertyArray.length ;i++) {
        var childArray = [];
        indexValue = 0;
        indexCondition = 0;
        for (indexCondition = 0, indexValue = 0; indexCondition < conditionArray.length && indexValue < valueArray[index].length;) {
            valueCondition = conditionArray[indexCondition];
            valueValue = valueArray[index][indexValue];
            if (valueCondition == valueValue) {
                childArray.push(valueArray[i][indexValue]);
                indexValue++;
                indexCondition++;
            } else if (valueCondition < valueValue) {
                indexCondition++;
                childArray.push(undefined);
            } else {
                indexValue++;
            }
        }
        while (indexCondition < conditionArray.length){
            childArray.push(undefined);
            indexCondition++;
        }
        rs.push(childArray);
    }
 	return rs;
}