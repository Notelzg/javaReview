/**
 * Copyright 2018 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel.aviator.function.string;

import com.googlecode.aviator.runtime.function.AbstractVariadicFunction;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorString;

import java.util.Map;

/**
 * @author lfjin
 */
public class ConcatenateFunction extends AbstractVariadicFunction {

    /*
     * (non-Javadoc)
     *
     * @see com.googlecode.aviator.runtime.type.AviatorFunction#getName()
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "string.concatenate";
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
        // TODO Auto-generated method stub
        StringBuffer sb = new StringBuffer();

        for (AviatorObject arg : args) {
            Object value = arg.getValue(env);
            double tempD;
            int tempI ;
            if (value != null) {
                if (value instanceof Number){
                    tempI = ((Number) value).intValue() ;
                    tempD = ((Number) value).doubleValue() ;
                    if ( tempD == Double.valueOf(String.valueOf(tempI))){
                        sb.append(tempI);
                    }else {
                        sb.append(value);
                    }
                } else {
                    sb.append(value);
                }
            }
        }

        return new AviatorString(sb.toString());
    }

}
