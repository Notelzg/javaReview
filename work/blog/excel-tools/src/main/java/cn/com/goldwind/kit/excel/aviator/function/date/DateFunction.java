/**
 * Copyright 2018 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel.aviator.function.date;

import java.util.Calendar;
import java.util.Map;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorString;

/**
 * @author lfjin
 *
 */
public class DateFunction extends AbstractFunction {

    /*
     * (non-Javadoc)
     *
     * @see com.googlecode.aviator.runtime.type.AviatorFunction#getName()
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "date.date";
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.googlecode.aviator.runtime.function.AbstractFunction#call(java.util.
     * Map, com.googlecode.aviator.runtime.type.AviatorObject)
     */
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2, AviatorObject arg3) {
        Number n_arg1 = FunctionUtils.getNumberValue(arg1, env);
        Number n_arg2 = FunctionUtils.getNumberValue(arg2, env);
        Number n_arg3 = FunctionUtils.getNumberValue(arg3, env);
        // TODO Auto-generated method stub
        Calendar cc = Calendar.getInstance();
        cc.set(Calendar.YEAR, n_arg1.intValue());
        cc.set(Calendar.MONTH, n_arg2.intValue() - 1);
        cc.set(Calendar.DATE, n_arg3.intValue());
        cc.set(Calendar.HOUR_OF_DAY, 0);
        cc.set(Calendar.MINUTE, 0);
        cc.set(Calendar.SECOND, 0);
        cc.set(Calendar.MILLISECOND, 0);

        return new AviatorString(DateFormat.getDateFormatter().format(cc.getTime()));
    }

}
