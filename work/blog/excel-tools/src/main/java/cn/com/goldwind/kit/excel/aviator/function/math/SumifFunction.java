/**
 * Copyright 2017 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel.aviator.function.math;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.runtime.function.AbstractVariadicFunction;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.util.Map;

/**
 * @author lfjin
 *
 */
public class SumifFunction extends AbstractVariadicFunction {

    /*
     * (non-Javadoc)
     *
     * @see com.googlecode.aviator.runtime.type.AviatorFunction#getName()
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "math.sumif";
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
        int len = args.length;
        if (len % 2 == 0) {
            throw new IllegalArgumentException("args must be odd");
        }

        int mid = len / 2;

        Object criteria = args[mid].getValue(env);

        String formula = "";

        if (criteria instanceof String) {
            String s_criteria = (String) criteria;
            if (s_criteria.startsWith("<>")) {
                formula = "x!=" + criteria.toString().substring(2) ;
            }else if (s_criteria.startsWith(">") || s_criteria.startsWith("<")) {
                formula = "x" + criteria.toString();
            } else {
                formula = "x=='" + criteria.toString() + "'";
            }
        } else {
            formula = "x==" + criteria.toString();
        }

        double rtn = 0.0f;
        for (int i = 0; i < mid; i++) {

            Object value = args[i].getValue(env);
            if (Boolean.TRUE.equals(AviatorEvaluator.exec(formula, value))) {
                Object sumValue = args[i + mid + 1].getValue(env);
                if (sumValue == null) {
                    continue;
                }
                if (sumValue instanceof Number) {
                    Number n_sumValue = (Number) sumValue;
                    rtn += n_sumValue.doubleValue();
                } else if (sumValue instanceof String) {
                    String s_sumValue = (String) sumValue;
                    double d = 0.0f;
                    if (s_sumValue.trim().length() > 0) {
                        try {
                            d = Double.valueOf(s_sumValue);
                        } catch (Exception e) {
                        }
                    }
                    rtn = rtn + d;
                }
            }
        }

        return new AviatorDouble(rtn);
    }

}
