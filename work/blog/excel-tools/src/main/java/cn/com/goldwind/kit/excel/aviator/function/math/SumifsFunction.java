/**
 * Copyright 2017 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel.aviator.function.math;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.runtime.function.AbstractVariadicFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lfjin
 *
 */
public class SumifsFunction extends AbstractVariadicFunction {

    /*
     * (non-Javadoc)
     *
     * @see com.googlecode.aviator.runtime.type.AviatorFunction#getName()
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "math.sumifs";
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
        int arrayLength = FunctionUtils.getNumberValue(args[0], env).intValue();// 第一个参数是数组长度
        /* -1 : 减去第一个参数 */
        if ((len - arrayLength -1) % (arrayLength + 1) != 0) {
            throw new IllegalArgumentException("args number error");
        }
        int loops = (len - arrayLength - 1) / (arrayLength + 1); // 一共多少个条件

        List<String> formulas = new ArrayList<String>();

        for (int n = 1; n <= loops; n++) {
            Object criteria = args[1 + arrayLength + n * arrayLength + (n - 1)].getValue(env);
            String formula = tradeFormula(criteria);
            formulas.add(formula);
        }

        double rtn = 0.0f;
        for (int i = 0; i < arrayLength; i++) {
            List values = getValues(arrayLength, loops, i, env, args);

            if (ifs(loops, formulas, values)) {
                Object sumValue = args[i + 1].getValue(env);
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

    private String tradeFormula(Object criteria) {
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

        return formula;

    }

    private List getValues(int arrayLength, int loops, int i, Map<String, Object> env, AviatorObject... args) {
        List values = new ArrayList<>();

        for (int n = 1; n <= loops; n++) {
            Object value = args[1 + i + n * arrayLength + (n - 1)].getValue(env);
            values.add(value);
        }
        return values;
    }

    private boolean ifs(int loops, List<String> formulas, List<Object> values) {
        for (int n = 0; n < loops; n++) {
            String formula = formulas.get(n);
            Object value = values.get(n);
            if (Boolean.FALSE.equals(AviatorEvaluator.exec(formula, value))) {
                return false;
            }
        }
        return true;
    }
}
