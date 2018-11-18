/**
 * Copyright 2018 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel.aviator.function.date;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;

/**
 * @author lfjin
 *
 */
public class MonthFunction extends AbstractFunction {

    /*
     * (non-Javadoc)
     *
     * @see com.googlecode.aviator.runtime.type.AviatorFunction#getName()
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "date.month";
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.googlecode.aviator.runtime.function.AbstractFunction#call(java.util.
     * Map, com.googlecode.aviator.runtime.type.AviatorObject)
     */
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1) {
        // TODO Auto-generated method stub
        Object s_date = arg1.getValue(env);
        if (s_date instanceof String) {
            Date date;
            try {
                date = DateFormat.getDateFormatter().parse(s_date.toString());

                Calendar cc = Calendar.getInstance();
                cc.setTime(date);

                return new AviatorDouble(cc.get(Calendar.MONTH) + 1);
            } catch (ParseException e) {
                throw new IllegalArgumentException("Error Date String : " + arg1.getValue(env).toString() + " .");
            }
        } else {
            throw new IllegalArgumentException("Argument must be String");
        }

    }

}
