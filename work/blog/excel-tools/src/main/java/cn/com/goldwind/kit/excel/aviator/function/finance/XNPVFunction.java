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
import in.satpathy.financial.XIRRData;
import in.satpathy.financial.XIRRNPV;
import in.satpathy.math.GoalSeekStatus;

/**
 * @author lfjin
 *
 */
public class XNPVFunction extends AbstractVariadicFunction {

    /*
     * (non-Javadoc)
     *
     * @see com.googlecode.aviator.runtime.type.AviatorFunction#getName()
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "finance.xnpv";
    }

    @Override
    public AviatorObject variadicCall(Map<String, Object> env, AviatorObject... args) {
        int len = args.length;
        int half = len / 2;
        double rate = FunctionUtils.getNumberValue(args[0], env).doubleValue();

        if (len % 2 == 0) {
            throw new IllegalArgumentException("Too less args");
        }

        double[] values = new double[half];
        double[] dates = new double[half];

        for (int i = 0; i < half; i++) {
            Object first = args[i + 1].getValue(env);
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
            Object first = args[half + i + 1].getValue(env);
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

        XIRRData data = new XIRRData(half, rate, values, dates);
        XIRRNPV xnpv = new XIRRNPV();
        GoalSeekStatus status = xnpv.f(rate, data);
        double result;
        if (status.seekStatus == GoalSeekStatus.GOAL_SEEK_OK) {
            result = ((Double) status.returnData).doubleValue(); // data.root ;
        } else {
            result = Double.NaN;
        }

        return new AviatorDouble(result);

    }
}
