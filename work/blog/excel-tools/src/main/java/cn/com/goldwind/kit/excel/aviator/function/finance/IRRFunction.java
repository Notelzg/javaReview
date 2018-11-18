/**
 * Copyright 2017 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel.aviator.function.finance;

import com.googlecode.aviator.runtime.function.AbstractVariadicFunction;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.ss.formula.functions.Irr;

import java.lang.reflect.Array;
import java.util.Map;

/**
 * @author lfjin
 *
 */
public class IRRFunction extends AbstractVariadicFunction {

    /*
     * (non-Javadoc)
     *
     * @see com.googlecode.aviator.runtime.type.AviatorFunction#getName()
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "finance.irr";
    }

    @Override
    public AviatorObject variadicCall(Map<String, Object> env, AviatorObject... args) {

        if (args.length < 2) {
            throw new IllegalArgumentException("Too less args");
        }
        Double[] dup = (Double[]) Array.newInstance(Double.class, args.length);
        for (int i = 0; i < args.length; i++) {
            Object first = args[i].getValue(env);
            if (first == null) {
                throw new NullPointerException("no value");
            }
            if (first instanceof Number) {
                dup[i] = ((Number) first).doubleValue();
            } else {
                throw new IllegalArgumentException(args[i].desc(env) + " contains none-number value");
            }
        }

        double irr = Irr.irr(ArrayUtils.toPrimitive(dup), 0.01D);
        return new AviatorDouble(irr);

    }
}
