/**
 * Copyright 2017 Goldwind Science & Technology. All rights reserved. GOLDWIND
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
public class IndexFunction extends AbstractVariadicFunction {

    /*
     * (non-Javadoc)
     *
     * @see com.googlecode.aviator.runtime.type.AviatorFunction#getName()
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "search.index";
    }

    /*
     * (non-Javadoc)
     *
     * @see com.googlecode.aviator.runtime.function.AbstractVariadicFunction#
     * variadicCall(java.util.Map,
     * com.googlecode.aviator.runtime.type.AviatorObject[])
     */
    /**
     * support INDEX(reference, row_num, column_num) usage search.index(cols,
     * refernce[], row, column)
     *
     */
    @Override
    public AviatorObject variadicCall(Map<String, Object> env, AviatorObject... args) {
        int len = args.length;
        if (len < 4) {
            throw new IllegalArgumentException("Arguments need at least 4 arguments");
        }

        // Object criteria = args[len - 1].getValue(env);
        int cols = FunctionUtils.getNumberValue(args[0], env).intValue();
        if ((len - 1) % cols != 2) {
            if (cols == 2 && (len - 1) % cols == 0) {
                // just ok.
            } else {
                throw new IllegalArgumentException("IllegalArguments count. usage search.index(cols, refernce[], row, column)");
            }
        }

        int row_num = FunctionUtils.getNumberValue(args[len - 2], env).intValue();
        int col_num = FunctionUtils.getNumberValue(args[len - 1], env).intValue();

        int index = cols * (row_num - 1) + col_num; // can not use (col_num-1),
                                                    // because arg[0] is used
                                                    // for cols, so if you
                                                    // use(col_num-1, you must
                                                    // use (index + 1) for
                                                    // position.
        if (index > 0 && index < len - 2) {
            return args[index];
        } else {
            return AviatorNil.NIL;
        }

    }

}
