/**
 * Copyright 2017 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel.aviator.function.math;

import java.util.Map;

import com.googlecode.aviator.runtime.function.AbstractVariadicFunction;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;

/**
 * @author lfjin
 *
 */
public class MaxFunction extends AbstractVariadicFunction {

    /*
     * (non-Javadoc)
     *
     * @see com.googlecode.aviator.runtime.type.AviatorFunction#getName()
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "math.max";
    }

    @Override
    public AviatorObject variadicCall(Map<String, Object> env, AviatorObject... args) {

        Number max = null;
        for (AviatorObject arg : args) {
            Object value = arg.getValue(env);
            if (value == null) {

            } else if (value instanceof String) {
                if (((String) value).trim().length() == 0) {

                } else {
                    try {
                        double d = Double.valueOf((String) value);
                        max = compare(max, d);
                    } catch (Exception e) {

                    }
                }
            } else if (value instanceof Number) {
                max = compare(max, (Number) value);
            }
        }
        if (max == null) {
            return new AviatorDouble(0f);
        } else {
            return new AviatorDouble(max);
        }
    }

    private Number compare(Number x, Number y) {
        if (x == null && y == null) {
            return null;
        } else if (x == null) {
            return y;
        } else if (y == null) {
            return x;
        } else {
            return Math.max(x.doubleValue(), y.doubleValue());
        }
    }
}
