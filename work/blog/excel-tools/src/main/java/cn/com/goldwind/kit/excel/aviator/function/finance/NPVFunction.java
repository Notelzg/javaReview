/**
 * Copyright 2017 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel.aviator.function.finance;

import java.lang.reflect.Array;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.ss.formula.functions.FinanceLib;

import com.googlecode.aviator.runtime.function.AbstractVariadicFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;

/**
 * @author lfjin
 *
 */
public class NPVFunction extends AbstractVariadicFunction {

    /*
     * (non-Javadoc)
     *
     * @see com.googlecode.aviator.runtime.type.AviatorFunction#getName()
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "finance.npv";
    }

    @Override
    public AviatorObject variadicCall(Map<String, Object> env, AviatorObject... args) {

        if (args.length < 3) {
            throw new IllegalArgumentException("Too less args");
        }
        Number r = FunctionUtils.getNumberValue(args[0], env);

        Double[] dup = (Double[]) Array.newInstance(Double.class, args.length - 1);
        for (int i = 1; i < args.length; i++) {
            Object first = args[i].getValue(env);
            if (first == null) {
                throw new NullPointerException("no value");
            }
            if (first instanceof Number) {
                dup[i - 1] = ((Number) first).doubleValue();
            } else {
                throw new IllegalArgumentException(args[i].desc(env) + " contains none-number value");
            }
        }

        double npv = FinanceLib.npv(r.doubleValue(), ArrayUtils.toPrimitive(dup));
        return new AviatorDouble(npv);

    }
}
