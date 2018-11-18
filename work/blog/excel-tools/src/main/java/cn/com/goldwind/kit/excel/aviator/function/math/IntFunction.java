/**
 * Copyright 2018 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel.aviator.function.math;

import java.math.BigDecimal;
import java.util.Map;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;

/**
 * @author lfjin
 *
 */
public class IntFunction extends AbstractFunction {

    /*
     * (non-Javadoc)
     *
     * @see com.googlecode.aviator.runtime.type.AviatorFunction#getName()
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "math.int";
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
        Number n_arg1 = FunctionUtils.getNumberValue(arg1, env);

        BigDecimal bg = new BigDecimal(n_arg1.doubleValue());
        int roundType = BigDecimal.ROUND_DOWN;
        if (n_arg1.doubleValue() < 0) {
            roundType = BigDecimal.ROUND_UP;
        }
        double f1 = bg.setScale(0, roundType).doubleValue();
        return new AviatorDouble(f1);

    }

}
