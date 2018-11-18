/**
 * Copyright 2018 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel.aviator.op;

import java.util.Map;

import com.googlecode.aviator.lexer.token.OperatorType;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;

/**
 * @author lfjin
 *
 */
public class MultOpFunction extends AbstractFunction {

    /*
     * (non-Javadoc)
     *
     * @see com.googlecode.aviator.runtime.type.AviatorFunction#getName()
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return OperatorType.MULT.token;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.googlecode.aviator.runtime.function.AbstractFunction#call(java.util.
     * Map, com.googlecode.aviator.runtime.type.AviatorObject,
     * com.googlecode.aviator.runtime.type.AviatorObject)
     */
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
        Object x = arg1.getValue(env);
        Object y = arg2.getValue(env);

        if (x == null || y == null) {
            return new AviatorDouble(0.0f);
        }

        return arg1.mult(arg2, env);

    }

}
