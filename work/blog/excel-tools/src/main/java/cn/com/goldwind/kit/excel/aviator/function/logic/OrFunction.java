/**
 * Copyright 2018 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel.aviator.function.logic;

import java.util.Map;

import com.googlecode.aviator.runtime.function.AbstractVariadicFunction;
import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorObject;

/**
 * @author lfjin
 *
 */
public class OrFunction extends AbstractVariadicFunction {

    /*
     * (non-Javadoc)
     *
     * @see com.googlecode.aviator.runtime.type.AviatorFunction#getName()
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "logic.or";
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
        for (AviatorObject arg : args) {

            Object first = arg.getValue(env);
            if (first == null) {
                continue;
            }

            Class<?> clazz = first.getClass();

            if (Boolean.class.isAssignableFrom(clazz)) {
                boolean tmp = ((Boolean) first).booleanValue();
                if (tmp) {
                    return AviatorBoolean.TRUE;
                } else {
                    continue;
                }
            } else {
                throw new IllegalArgumentException(arg.desc(env) + " is not a number value");
            }

        }
        return AviatorBoolean.FALSE;
    }

}
