/**
 * Copyright 2017 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel.aviator.function.math;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import com.googlecode.aviator.runtime.function.AbstractVariadicFunction;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;

/**
 * @author lfjin
 *
 */
public class AverageFunction extends AbstractVariadicFunction {

    /*
     * (non-Javadoc)
     *
     * @see com.googlecode.aviator.runtime.type.AviatorFunction#getName()
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "math.avg";
    }

    /*
     * (non-Javadoc)
     *
     * @see com.googlecode.aviator.runtime.function.AbstractVariadicFunction#
     * variadicCall(java.util.Map,
     * com.googlecode.aviator.runtime.type.AviatorObject[])
     */
    @Override
    public AviatorObject variadicCall(Map<String, Object> env, AviatorObject... args) {
        double rtn = 0.0f;
        int count = 0;
        for (AviatorObject arg : args) {

            Object first = arg.getValue(env);
            if (first == null) {
                continue;
            }

            Class<?> clazz = first.getClass();

            if (List.class.isAssignableFrom(clazz)) {
                List<?> list = (List<?>) first;
                int length = list.size();
                Double[] dup = (Double[]) Array.newInstance(Double.class, length);

                for (int i = 0; i < length; i++) {
                    Object o = list.get(i);

                    if (o instanceof Number) {
                        dup[i] = ((Number) o).doubleValue();
                    } else {
                        throw new IllegalArgumentException(arg.desc(env) + " contains none-number value");
                    }
                }

                double sub = addEveryone(ArrayUtils.toPrimitive(dup));
                rtn = rtn + sub;
                count = count + length;
            } else if (clazz.isArray()) {
                int length = Array.getLength(first);
                Double[] dup = (Double[]) Array.newInstance(Double.class, length);

                for (int i = 0; i < length; i++) {
                    dup[i] = Array.getDouble(first, i);
                }

                double sub = addEveryone(ArrayUtils.toPrimitive(dup));
                rtn = rtn + sub;
                count = count + length;

            } else if (Number.class.isAssignableFrom(clazz)) {
                rtn = rtn + ((Number) first).doubleValue();
                count = count + 1;
            } else if (String.class.equals(clazz)) {
                String s = (String) first;
                double d = 0.0f;
                if (s.trim().length() > 0) {
                    try {
                        d = Double.valueOf(s);
                        rtn = rtn + d;
                        count = count + 1;
                    } catch (Exception e) {
                    }
                }
            } else {
                throw new IllegalArgumentException(arg.desc(env) + " is not a number value");
            }
        }
        return new AviatorDouble(rtn / count);
    }

    private double addEveryone(double[] seq) {
        double rtn = 0.0f;
        for (double d : seq) {
            rtn = rtn + d;
        }
        return rtn;
    }
}
