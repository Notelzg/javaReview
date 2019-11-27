/**
 * Copyright 2017 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel.aviator.function.finance;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.googlecode.aviator.runtime.function.AbstractVariadicFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;

import cn.com.goldwind.kit.excel.aviator.function.date.DateFormat;
import in.satpathy.financial.XIRR;
import in.satpathy.financial.XIRRData;

/**
 * @author lfjin
 *
 */
public class XIRRFunction extends AbstractVariadicFunction {

    /*
     * (non-Javadoc)
     *
     * @see com.googlecode.aviator.runtime.type.AviatorFunction#getName()
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "finance.xirr";
    }

    @Override
    public AviatorObject variadicCall(Map<String, Object> env, AviatorObject... args) {
        int len = args.length;
        int half = len / 2;
        /*
        Excel 使用一种迭代技术来计算Xirr。 通过使用更改的速率 (从 guess 开始), Xirr将循环遍历计算,
         直到结果在 0.000001% 范围内得到准确。 如果Xirr找不到在100尝试之后工作的结果, 则 #NUM! 返回错误值。
         所以精度改为和微软的一样，次数改为100次。
         */
        double guess = 0.00000001;

        if (args.length < 2) {
            throw new IllegalArgumentException("Too less args");
        }

        if (len % 2 == 1) {
            guess = FunctionUtils.getNumberValue(args[len - 1], env).doubleValue();
        }

        double[] values = new double[half];
        double[] dates = new double[half];

        for (int i = 0; i < half; i++) {
            Object first = args[i].getValue(env);
            if (first == null) {
                throw new NullPointerException("no value");
            }
            if (first instanceof Number) {
                values[i] = ((Number) first).doubleValue();
            } else {
                throw new IllegalArgumentException(args[i].desc(env) + " contains none-number value");
            }
        }
        for (int i = 0; i < half; i++) {
            Object first = args[half + i].getValue(env);
            if (first == null) {
                throw new NullPointerException("no value");
            }
            if (first instanceof String) {
                Date date;
                try {
                    date = DateFormat.getDateFormatter().parse(first.toString());

                    Calendar cc = Calendar.getInstance();
                    cc.setTime(date);

                    dates[i] = XIRRData.getExcelDateValue(cc);
                } catch (ParseException e) {
                    throw new IllegalArgumentException("Error Date String : " + args[i].desc(env) + ":"
                            + first.toString() + " .");
                }
            } else {
                throw new IllegalArgumentException(args[i].desc(env) + " contains none-number value");
            }
        }

        XIRRData data = new XIRRData(half, guess, values, dates);
        double xirrValue = XIRR.xirr(data);

        return new AviatorDouble(xirrValue);

    }
}
