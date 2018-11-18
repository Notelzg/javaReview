/**
 * Copyright 2018 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel.aviator.function.finance;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;
import org.apache.poi.ss.formula.functions.Finance;

import java.util.Map;

/**
 * @author lfjin
 *
 */
public class PMTFunction extends AbstractFunction {

    /*
     * (non-Javadoc)
     *
     * @see com.googlecode.aviator.runtime.type.AviatorFunction#getName()
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "finance.pmt";
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.googlecode.aviator.runtime.function.AbstractFunction#call(java.util.
     * Map, com.googlecode.aviator.runtime.type.AviatorObject,
     * com.googlecode.aviator.runtime.type.AviatorObject,
     * com.googlecode.aviator.runtime.type.AviatorObject,
     * com.googlecode.aviator.runtime.type.AviatorObject)
     */
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2, AviatorObject arg3) {
        // TODO Auto-generated method stub
        return call(env, arg1, arg2, arg3, new AviatorDouble(0), new AviatorDouble(0));
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.googlecode.aviator.runtime.function.AbstractFunction#call(java.util.
     * Map, com.googlecode.aviator.runtime.type.AviatorObject,
     * com.googlecode.aviator.runtime.type.AviatorObject,
     * com.googlecode.aviator.runtime.type.AviatorObject,
     * com.googlecode.aviator.runtime.type.AviatorObject,
     * com.googlecode.aviator.runtime.type.AviatorObject)
     */
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2, AviatorObject arg3,
            AviatorObject arg4) {
        // TODO Auto-generated method stub
        return call(env, arg1, arg2, arg3, arg4, new AviatorDouble(0));
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.googlecode.aviator.runtime.function.AbstractFunction#call(java.util.
     * Map, com.googlecode.aviator.runtime.type.AviatorObject,
     * com.googlecode.aviator.runtime.type.AviatorObject,
     * com.googlecode.aviator.runtime.type.AviatorObject,
     * com.googlecode.aviator.runtime.type.AviatorObject,
     * com.googlecode.aviator.runtime.type.AviatorObject,
     * com.googlecode.aviator.runtime.type.AviatorObject)
     */
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2, AviatorObject arg3,
            AviatorObject arg4, AviatorObject arg5) {
        // TODO Auto-generated method stub
        Number n_arg1 = FunctionUtils.getNumberValue(arg1, env);
        Number n_arg2 = FunctionUtils.getNumberValue(arg2, env);
        Number n_arg3 = FunctionUtils.getNumberValue(arg3, env);
        Number n_arg4 = FunctionUtils.getNumberValue(arg4, env);
        Number n_arg5 = FunctionUtils.getNumberValue(arg5, env);

        double r = Finance.pmt(n_arg1.doubleValue(), n_arg2.intValue(), n_arg3.intValue(), n_arg4.doubleValue(),
                                n_arg5.intValue());
        if (Double.isNaN(r)) {
            r = 0.0;
        }
        return new AviatorDouble(r);
    }

}
