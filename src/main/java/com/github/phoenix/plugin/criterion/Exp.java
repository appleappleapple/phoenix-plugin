package com.github.phoenix.plugin.criterion;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 原子表达式类
 *
 * @author HuQingmiao
 */
public class Exp {

    private static Logger log = LoggerFactory.getLogger(Exp.class);

    /**
     * Apply a "equal" constraint to the named property
     *
     * @param propertyName
     * @param value
     * @return Criterion
     */
    public static SimpleExpression eq(String propertyName, Object value) {
        return new SimpleExpression(propertyName, value, "=");
    }

    public static SimpleExpression eq(String propertyName, int value) {
        return eq(propertyName, new Integer(value));
    }

    public static SimpleExpression eq(String propertyName, long value) {
        return eq(propertyName, new Long(value));
    }

    public static SimpleExpression eq(String propertyName, float value) {
        return eq(propertyName, new Float(value));
    }

    public static SimpleExpression eq(String propertyName, double value) {
        return eq(propertyName, new Double(value));
    }

    public static SimpleExpression eq(String propertyName, char value) {
        return eq(propertyName, new Character(value));
    }

    /**
     * Apply a "not equal" constraint to the named property
     *
     * @param propertyName
     * @param value
     * @return Criterion
     */
    public static SimpleExpression ne(String propertyName, Object value) {
        return new SimpleExpression(propertyName, value, "<>");
    }

    public static SimpleExpression ne(String propertyName, int value) {
        return ne(propertyName, new Integer(value));
    }

    public static SimpleExpression ne(String propertyName, long value) {
        return ne(propertyName, new Long(value));
    }

    public static SimpleExpression ne(String propertyName, float value) {
        return ne(propertyName, new Float(value));
    }

    public static SimpleExpression ne(String propertyName, double value) {
        return ne(propertyName, new Double(value));
    }

    public static SimpleExpression ne(String propertyName, char value) {
        return ne(propertyName, new Character(value));
    }

    /**
     * Apply a "like" constraint to the named property
     *
     * @param propertyName
     * @param value
     * @return Criterion
     */
    public static LikeExpression like(String propertyName, String value) {
        return new LikeExpression(propertyName, value);
    }


    /**
     * Apply a "like" constraint to the named property
     *
     * @param propertyName
     * @param value
     * @return Criterion
     */
    public static LikeExpression like(String propertyName, String value, char escapeChar) {
        return new LikeExpression(propertyName, value, escapeChar);
    }


    /**
     * Apply a "greater than" constraint to the named property
     *
     * @param propertyName
     * @param value
     * @return Criterion
     */
    public static SimpleExpression gt(String propertyName, Object value) {
        return new SimpleExpression(propertyName, value, ">");
    }

    public static SimpleExpression gt(String propertyName, int value) {
        return gt(propertyName, new Integer(value));
    }

    public static SimpleExpression gt(String propertyName, long value) {
        return gt(propertyName, new Long(value));
    }

    public static SimpleExpression gt(String propertyName, float value) {
        return gt(propertyName, new Float(value));
    }

    public static SimpleExpression gt(String propertyName, double value) {
        return gt(propertyName, new Double(value));
    }

    public static SimpleExpression gt(String propertyName, char value) {
        return gt(propertyName, new Character(value));
    }

    /**
     * Apply a "less than" constraint to the named property
     *
     * @param propertyName
     * @param value
     * @return Criterion
     */
    public static SimpleExpression lt(String propertyName, Object value) {
        return new SimpleExpression(propertyName, value, "<");
    }

    public static SimpleExpression lt(String propertyName, int value) {
        return lt(propertyName, new Integer(value));
    }

    public static SimpleExpression lt(String propertyName, long value) {
        return lt(propertyName, new Long(value));
    }

    public static SimpleExpression lt(String propertyName, float value) {
        return lt(propertyName, new Float(value));
    }

    public static SimpleExpression lt(String propertyName, double value) {
        return lt(propertyName, new Double(value));
    }

    public static SimpleExpression lt(String propertyName, char value) {
        return lt(propertyName, new Character(value));
    }

    /**
     * Apply a "less than or equal" constraint to the named property
     *
     * @param propertyName
     * @param value
     * @return Criterion
     */
    public static SimpleExpression le(String propertyName, Object value) {
        return new SimpleExpression(propertyName, value, "<=");
    }

    public static SimpleExpression le(String propertyName, int value) {
        return le(propertyName, new Integer(value));
    }

    public static SimpleExpression le(String propertyName, long value) {
        return le(propertyName, new Long(value));
    }

    public static SimpleExpression le(String propertyName, float value) {
        return le(propertyName, new Float(value));
    }

    public static SimpleExpression le(String propertyName, double value) {
        return le(propertyName, new Double(value));
    }

    public static SimpleExpression le(String propertyName, char value) {
        return le(propertyName, new Character(value));
    }

    /**
     * Apply a "greater than or equal" constraint to the named property
     *
     * @param propertyName
     * @param value
     * @return Criterion
     */
    public static SimpleExpression ge(String propertyName, Object value) {
        return new SimpleExpression(propertyName, value, ">=");
    }

    public static SimpleExpression ge(String propertyName, int value) {
        return ge(propertyName, new Integer(value));
    }

    public static SimpleExpression ge(String propertyName, long value) {
        return ge(propertyName, new Long(value));
    }

    public static SimpleExpression ge(String propertyName, float value) {
        return ge(propertyName, new Float(value));
    }

    public static SimpleExpression ge(String propertyName, double value) {
        return ge(propertyName, new Double(value));
    }

    public static SimpleExpression ge(String propertyName, char value) {
        return ge(propertyName, new Character(value));
    }

    /**
     * Apply a "between" constraint to the named property
     *
     * @param propertyName
     * @param lo           value
     * @param hi           value
     * @return Criterion
     */
    public static BetweenExpression between(String propertyName, Object lo, Object hi) {
        return new BetweenExpression(propertyName, lo, hi);
    }

    public static BetweenExpression between(String propertyName, int lo, int hi) {
        return between(propertyName, new Integer(lo), new Integer(hi));
    }

    public static BetweenExpression between(String propertyName, long lo, long hi) {
        return between(propertyName, new Long(lo), new Long(hi));
    }

    public static BetweenExpression ge(String propertyName, float lo, float hi) {
        return between(propertyName, new Float(lo), new Float(hi));
    }

    public static BetweenExpression ge(String propertyName, double lo, double hi) {
        return between(propertyName, new Double(lo), new Double(hi));
    }

    public static BetweenExpression ge(String propertyName, char lo, char hi) {
        return between(propertyName, String.valueOf(lo), String.valueOf(hi));
    }

    /**
     * Apply an "in" constraint to the named property
     *
     * @param propertyName
     * @param values
     * @return Criterion
     */
    public static Criteria in(String propertyName, Object[] values) {
        return new InExpression(propertyName, values);
    }

    public static Criteria in(String propertyName, int[] values) {

        Integer[] v = new Integer[values.length];
        for (int i = 0; i < values.length; i++) {
            v[i] = new Integer(values[i]);
        }
        return new InExpression(propertyName, v);
    }

    public static Criteria in(String propertyName, long[] values) {
        Long[] v = new Long[values.length];
        for (int i = 0; i < values.length; i++) {
            v[i] = new Long(values[i]);
        }
        return new InExpression(propertyName, v);
    }

    public static Criteria in(String propertyName, char[] values) {
        String[] v = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            v[i] = String.valueOf(values[i]);
        }
        return new InExpression(propertyName, v);
    }

    public static Criteria in(String propertyName, double[] values) {
        Double[] v = new Double[values.length];
        for (int i = 0; i < values.length; i++) {
            v[i] = new Double(values[i]);
        }
        return new InExpression(propertyName, v);
    }

    public static Criteria in(String propertyName, float[] values) {
        Float[] v = new Float[values.length];
        for (int i = 0; i < values.length; i++) {
            v[i] = new Float(values[i]);
        }
        return new InExpression(propertyName, v);
    }

    /**
     * Apply an "in" constraint to the named property
     *
     * @param propertyName
     * @param values
     * @return Criterion
     */
    public static Criteria in(String propertyName, Collection<?> values) {
        return new InExpression(propertyName, values.toArray());
    }


    /**
     * Return the conjuction of two expressions
     *
     * @param lhs
     * @param rhs
     * @return Criterion
     */
    public static CriteriaGroup and(Criteria lhs, Criteria rhs) {
        try {
            CriteriaGroup cg = new CriteriaGroup(CriteriaGroup.and);
            cg.add(lhs).add(rhs);
            return cg;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Return the disjuction of two expressions
     *
     * @param lhs
     * @param rhs
     * @return Criterion
     */
    public static CriteriaGroup or(Criteria lhs, Criteria rhs) {
        try {
            CriteriaGroup cg = new CriteriaGroup(CriteriaGroup.or);
            cg.add(lhs).add(rhs);
            return cg;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }


}
