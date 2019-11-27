/**
 * Copyright 2017 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel.aviator.function.math;

import com.googlecode.aviator.runtime.function.AbstractVariadicFunction;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author lfjin
 *
 */
public class SumFunction extends AbstractVariadicFunction {

    /*
     * (non-Javadoc)
     *
     * @see com.googlecode.aviator.runtime.type.AviatorFunction#getName()
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "math.sum";
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
        BigDecimal rtn = new BigDecimal(0.0f);
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

                BigDecimal sub = addEveryone(ArrayUtils.toPrimitive(dup));
                if (Double.isFinite(sub.doubleValue()) && Double.isFinite(rtn.doubleValue()))
                    rtn = rtn.add(sub);
                else
                    new AviatorDouble(Double.NaN);

            } else if (clazz.isArray()) {
                int length = Array.getLength(first);
                Double[] dup = (Double[]) Array.newInstance(Double.class, length);

                for (int i = 0; i < length; i++) {
                    dup[i] = Array.getDouble(first, i);
                }

                BigDecimal sub = addEveryone(ArrayUtils.toPrimitive(dup));
                if (Double.isFinite(sub.doubleValue()) && Double.isFinite(rtn.doubleValue()))
                    rtn = rtn.add(sub);
                else
                    new AviatorDouble(Double.NaN);


            } else if (Number.class.isAssignableFrom(clazz)) {
                double sub =  ((Number) first).doubleValue();
                if (Double.isFinite(sub) && Double.isFinite(rtn.doubleValue()))
                    rtn = rtn.add(new BigDecimal(String.valueOf(sub)));
                else
                    new AviatorDouble(Double.NaN);
            } else if (String.class.equals(clazz)) {
                String s = (String) first;
                double d = 0.0f;
                if (s.trim().length() > 0) {
                    try {
                        d = Double.valueOf(s);
                    } catch (Exception e) {
                    }
                }
                if (Double.isFinite(d) && Double.isFinite(rtn.doubleValue()))
                    rtn = rtn.add(new BigDecimal(String.valueOf(d)));
                else
                    new AviatorDouble(Double.NaN);
            } else {
                throw new IllegalArgumentException(arg.desc(env) + " is not a number value");
            }
        }
        return new AviatorDouble(rtn.doubleValue());
    }

    private BigDecimal addEveryone(double[] seq) {
        BigDecimal rs = new BigDecimal(0.0f);
        BigDecimal temp  ;
        for (double d : seq) {
            if (Double.isFinite(d) && Double.isFinite(rs.doubleValue())) {
                    temp = new BigDecimal(String.valueOf(d));
                    rs = rs.add(temp);
            }else {
                return new BigDecimal(Double.NaN);
            }
        }
        return rs;
    }
}
