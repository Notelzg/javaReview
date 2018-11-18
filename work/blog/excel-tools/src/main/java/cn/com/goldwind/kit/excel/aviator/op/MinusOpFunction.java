/**
 * Copyright 2018 Goldwind Science & Technology. All rights reserved. GOLDWIND
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.com.goldwind.kit.excel.aviator.op;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import com.googlecode.aviator.lexer.token.OperatorType;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorNil;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorString;

import cn.com.goldwind.kit.excel.aviator.function.date.DateFormat;
import in.satpathy.financial.XIRRData;

/**
 * @author lfjin
 *
 */
public class MinusOpFunction extends AbstractFunction {

    /*
     * (non-Javadoc)
     *
     * @see com.googlecode.aviator.runtime.type.AviatorFunction#getName()
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return OperatorType.SUB.token;
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
            return innerSub(new AviatorDouble(0.0f), arg2, env);
        }

        if (x instanceof Number) {
            return innerSub(arg1, arg2, env);
        } else if (x instanceof String) {
            try { // 先试是不是可以是数字
                double d = Double.valueOf(x.toString());
                return innerSub(new AviatorDouble(d), arg2, env);
            } catch (Exception e) {
                try { // 再看是不是日期
                    try {
                        Date date = DateFormat.getFullDatetimeFormatter().parse(x.toString());
                        Calendar cc = new GregorianCalendar(date.getYear() + 1900, date.getMonth(), date.getDate());
                        return innerDateSub(cc, arg2, env, Calendar.DATE);
                    } catch (Exception de) {
                        Date date = DateFormat.getDateFormatter().parse(x.toString());
                        Calendar cc = new GregorianCalendar(date.getYear() + 1900, date.getMonth(), date.getDate());
                        return innerDateSub(cc, arg2, env, Calendar.MONTH);
                    }
                } catch (Exception e1) { // 都不是
                    return new AviatorString(x.toString()).add(arg2, env);
                }
            }
        } else {
            return AviatorNil.NIL;
        }

    }

    public AviatorObject innerDateSub(Calendar self, AviatorObject other, Map<String, Object> env, int field) {
        Object y = other.getValue(env);
        if (y instanceof String) {
            try { // 再看是不是日期
                Date date = DateFormat.getDateFormatter().parse(y.toString());
                Calendar cc = new GregorianCalendar(date.getYear() + 1900, date.getMonth(), date.getDate());
                int days = XIRRData.getDaysBetween(self, cc);

                if (self.before(cc)) {
                    days = days * -1;
                }

                return new AviatorDouble(days);
            } catch (Exception e1) { // 都不是
                return AviatorNil.NIL;
            }
        }

        return AviatorNil.NIL;
    }

    public AviatorObject innerSub(AviatorObject self, AviatorObject other, Map<String, Object> env) {

        Object y = other.getValue(env);
        if (y instanceof Number) {
            return self.sub(other, env);
        } else if (y instanceof String) {
            try { // 先试是不是可以是数字
                double d = Double.valueOf(y.toString());
                return self.sub(new AviatorDouble(d), env);
            } catch (Exception e) {
                return AviatorNil.NIL;
            }
        }

        return AviatorNil.NIL;
    }

}
