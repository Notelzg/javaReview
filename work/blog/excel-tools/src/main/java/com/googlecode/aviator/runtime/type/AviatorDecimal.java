package com.googlecode.aviator.runtime.type;

import com.googlecode.aviator.AviatorEvaluator;

import java.math.BigDecimal;
import java.util.Map;


/**
 * Aviator Big Decimal
 * 
 * @since 2.3.0
 * @author dennis<killme2008@gmail.com>
 * 
 */
public class AviatorDecimal extends AviatorNumber {

  public AviatorDecimal(Number number) {
    super(number);
  }


  public static final AviatorDecimal valueOf(BigDecimal d) {
    return new AviatorDecimal(d);
  }


  public static final AviatorDecimal valueOf(String d) {
    return new AviatorDecimal(new BigDecimal(d, AviatorEvaluator.getMathContext()));
  }


  @Override
  public AviatorObject innerSub(AviatorNumber other) {
    switch (other.getAviatorType()) {
      case Double:
        return AviatorDouble.valueOf(this.doubleValue() - other.doubleValue());
      default:
        return AviatorDecimal.valueOf(
            this.toDecimal().subtract(other.toDecimal(), AviatorEvaluator.getMathContext()));
    }
  }


  @Override
  public AviatorObject neg(Map<String, Object> env) {
    return AviatorDecimal.valueOf(this.toDecimal().negate());
  }


  @Override
  public AviatorObject innerMult(AviatorNumber other) {
    switch (other.getAviatorType()) {
      case Double:
        return AviatorDouble.valueOf(this.doubleValue() * other.doubleValue());
      default:
        return AviatorDecimal.valueOf(
            this.toDecimal().multiply(other.toDecimal(), AviatorEvaluator.getMathContext()));
    }
  }


  @Override
  public AviatorObject innerMod(AviatorNumber other) {
    switch (other.getAviatorType()) {
      case Double:
        return AviatorDouble.valueOf(this.doubleValue() % other.doubleValue());
      default:
        return AviatorDecimal.valueOf(
            this.toDecimal().remainder(other.toDecimal(), AviatorEvaluator.getMathContext()));
    }
  }


  @Override
  public AviatorObject innerDiv(AviatorNumber other) {
    switch (other.getAviatorType()) {
      case Double:
        return AviatorDouble.valueOf(this.doubleValue() / other.doubleValue());
      default:
        if (Double.isFinite(other.doubleValue()) && Double.isFinite(this.number.doubleValue())) {
          if (0 == Double.compare(other.doubleValue(), 0.0d) || 0 == Double.compare(other.doubleValue(), -0.0d) )
            return  new AviatorDouble(Double.NaN);
          BigDecimal a = new BigDecimal(String.valueOf(other.doubleValue()));
          BigDecimal b = new BigDecimal(String.valueOf(this.number.doubleValue()));
          b = b.divide(a,20,BigDecimal.ROUND_HALF_UP);
          return new AviatorDouble(b);
        }
        return new AviatorDouble(Double.NaN);
    }
  }


  @Override
  public AviatorNumber innerAdd(AviatorNumber other) {
    switch (other.getAviatorType()) {
      case Double:
        return AviatorDouble.valueOf(this.doubleValue() + other.doubleValue());
      default:
        return AviatorDecimal
            .valueOf(this.toDecimal().add(other.toDecimal(), AviatorEvaluator.getMathContext()));
    }
  }


  @Override
  public int innerCompare(AviatorNumber other) {
    switch (other.getAviatorType()) {
      case Double:
        return Double.compare(this.doubleValue(), other.doubleValue());
      default:
        return this.toDecimal().compareTo(other.toDecimal());
    }

  }


  @Override
  public AviatorType getAviatorType() {
    return AviatorType.Decimal;
  }

}
