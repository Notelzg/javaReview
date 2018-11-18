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
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorString;

/**
 * @author lfjin
 *
 */
public class EomonthFunction extends AbstractFunction {

    /*
     * (non-Javadoc)
     *
     * @see com.googlecode.aviator.runtime.type.AviatorFunction#getName()
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "date.eomonth";
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.googlecode.aviator.runtime.function.AbstractFunction#call(java.util.
     * Map, com.googlecode.aviator.runtime.type.AviatorObject)
     */
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
        Number n_arg2 = FunctionUtils.getNumberValue(arg2, env);
        // TODO Auto-generated method stub
        Object s_date = arg1.getValue(env);
        if (s_date instanceof String) {
            Date date;
            try {
                date = DateFormat.getDateFormatter().parse(s_date.toString());

                Calendar cc = Calendar.getInstance();
                cc.setTime(date);
                cc.add(Calendar.MONTH, n_arg2.intValue() + 1);
                cc.set(Calendar.DATE, 1);
                cc.add(Calendar.DATE, -1);

                return new AviatorString(DateFormat.getDateFormatter().format(cc.getTime()));
            } catch (ParseException e) {
                throw new IllegalArgumentException("Error Date String : " + arg1.getValue(env).toString() + " .");
            }
        } else {
            throw new IllegalArgumentException("Argument must be String");
        }

    }

}
