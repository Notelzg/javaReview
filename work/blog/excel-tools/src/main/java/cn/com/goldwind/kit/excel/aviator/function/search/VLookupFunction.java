/**
 * Copyright 2018 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel.aviator.function.search;

import com.googlecode.aviator.runtime.function.AbstractVariadicFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorNil;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.util.Map;

/**
 * @author lfjin
 *
 */
public class VLookupFunction extends AbstractVariadicFunction {

    /*
     * (non-Javadoc)
     *
     * @see com.googlecode.aviator.runtime.type.AviatorFunction#getName()
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "search.vlookup";
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
        // HLOOKUP(lookup_value, table_array, row_index_num, [range_lookup])
        // TODO Auto-generated method stub
        int len = args.length;

        if (args.length < 4) {
            throw new IllegalArgumentException("Too less args");
        }

        Object keyword = args[0].getValue(env);
        int cols = FunctionUtils.getNumberValue(args[1], env).intValue();
        int offset = 2; // 如果有false，那么就是倒数第三个
        if ((len - 2) % cols == 1) {
            offset = 1;
        }
        int colsIndex = FunctionUtils.getNumberValue(args[len - offset], env).intValue();

        int pos = 0;
        for (pos = 0; pos < len - 2;) {
            AviatorObject first = args[pos + 2];
            Object firstObject = first.getValue(env);

            if (keyword instanceof Number) {
                if (firstObject instanceof Number) {
                    if (((Number) keyword).doubleValue() == ((Number) firstObject).doubleValue()) {
                        break;
                    }
                }
            } else if (keyword instanceof String) {
                if (((String) keyword).equals(String.valueOf(firstObject))) {
                    break;
                }
            }

            pos = pos + cols;
        }
        try {
            return args[2 + pos - 1 + colsIndex];
        } catch (Exception e) {
            return AviatorNil.NIL;
        }
    }

}
