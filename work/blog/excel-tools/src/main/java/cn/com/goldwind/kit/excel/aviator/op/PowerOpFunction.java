/**
 * Copyright 2018 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel.aviator.op;

import com.googlecode.aviator.lexer.token.OperatorType;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorNil;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.util.Map;

/**
 * @author lfjin
 *
 */
public class PowerOpFunction extends AbstractFunction {

    /*
     * (non-Javadoc)
     *
     * @see com.googlecode.aviator.runtime.type.AviatorFunction#getName()
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return OperatorType.BIT_XOR.token;
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

        if (x == null) {
            return new AviatorDouble(0.0f);
        }

        if (x instanceof Number) {
            return innerPower(arg1, arg2, env);
        } else if (x instanceof String) {
            try { // 先试是不是可以是数字
                double d = Double.valueOf(x.toString());
                return innerPower(new AviatorDouble(d), arg2, env);
            } catch (Exception e) {
                return AviatorNil.NIL;
            }
        } else {
            return AviatorNil.NIL;
        }

    }


    public AviatorObject innerPower(AviatorObject self, AviatorObject other, Map<String, Object> env) {

        Object y = other.getValue(env);
        Number n_self = FunctionUtils.getNumberValue(self, env);
        Number d = null;
        if (y instanceof Number) {
            d= FunctionUtils.getNumberValue(other, env);
        } else if (y instanceof String) {
            try { // 先试是不是可以是数字
                d = Double.valueOf(y.toString());
            } catch (Exception e) {
                return AviatorNil.NIL;
            }
        } else {
            return AviatorNil.NIL;
        }


        double rtn = Math.pow(n_self.doubleValue(), d.doubleValue());
        return new AviatorDouble(rtn);
    }
}
