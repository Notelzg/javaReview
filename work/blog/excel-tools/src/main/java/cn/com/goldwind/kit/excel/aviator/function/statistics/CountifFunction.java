/**
 * Copyright 2017 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel.aviator.function.statistics;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.runtime.function.AbstractVariadicFunction;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.util.Map;

/**
 * @author lfjin
 *
 */
public class CountifFunction extends AbstractVariadicFunction {

    /*
     * (non-Javadoc)
     *
     * @see com.googlecode.aviator.runtime.type.AviatorFunction#getName()
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "statistics.countif";
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
        if (len < 2) {
            throw new IllegalArgumentException("Arguments need at least 2 arguments");
        }

        Object criteria = args[len - 1].getValue(env);

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

        int count = 0;
        for (int i = 0; i < len - 1; i++) {

            Object value = args[i].getValue(env);
            if (Boolean.TRUE.equals(AviatorEvaluator.exec(formula, value))) {
                count++;
            }
        }

        return new AviatorDouble(count);
    }

}
