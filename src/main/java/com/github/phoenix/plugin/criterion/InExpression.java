package com.github.phoenix.plugin.criterion;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * In
 *
 * @author HuQingmiao
 */
class InExpression extends Criteria {

    private final String colLabel;

    private final Object[] values;

    protected InExpression(String colLabel, Object[] values) {
        this.colLabel = colLabel;
        this.values = values;
    }

    public String toSqlString() {
        if (values == null || values.length == 0) {
            return " 1 = 2 ";
        }
        // return colName + " IN (" + toString(values) + ")";
        return buildInClause(colLabel, values);
    }


    /**
     * 构造形如" A.COLUMN IN (a,b,c,...)"的IN子句. 若参数set为空, 则返回 "1=1". 若参数 set为NULL,
     * 将会抛出java.lang.NullPointerException.
     *
     * @param colName 列名,或带有表的别名的列名
     * @param set     含字符或数字类型元素的集合
     * @return 返回形如" A.COLUMN IN (a,b,c,...)"的IN子句; 但若参数array长度为0, 则返回 "1=1"
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static String buildInClause(String colName, Set set) {

        final int maxItemCnt = 1000; // in子句允许的最大项数

        if (set.isEmpty()) {
            return " 1 =1 ";
        }

        StringBuffer buff = new StringBuffer();

        if (set.size() <= maxItemCnt) {
            buff.append(colName);
            buff.append(" IN (" + toString(set) + ") ");

        } else {

            // 将集合减裁后分别存放其中对象的子集合
            Set[] subSetArray = new HashSet[(set.size() - 1) / maxItemCnt + 1];

            // 将子集合初始化
            for (int i = 0; i < subSetArray.length; i++) {
                subSetArray[i] = new HashSet();
            }

            int i = 0;// 从第一个元素开始迭代
            for (Iterator it = set.iterator(); it.hasNext(); ) {
                Object obj = it.next();

                // 计算当前对象应该复制到哪个子集合
                int idx = i / maxItemCnt;

                subSetArray[idx].add(obj);
                i++;
            }

            buff.append(" ( ");
            buff.append(colName);
            buff.append(" IN (" + toString(subSetArray[0]) + ") ");

            for (i = 1; i < subSetArray.length; i++) {

                buff.append(" OR ");
                buff.append(colName);
                buff.append(" IN (" + toString(subSetArray[i]) + ") ");
            }

            buff.append(" ) ");

            for (i = 0; i < subSetArray.length; i++) {
                subSetArray[i].clear();
                subSetArray[i] = null;
            }
        }

        return buff.toString();
    }

    /**
     * 构造形如" A.COLUMN IN (a,b,c,...)"的IN子句. 若参数array长度为0, 则返回 "1=1".
     *
     * @param colName 列名,或带有表的别名的列名.
     * @param array   含字符或数字类型元素的数组
     * @return 返回形如" A.COLUMN IN (a,b,c,...)"的IN子句; 但若参数array长度为0, 则返回 "1=1"
     */

    public static String buildInClause(String colName, Object[] array) {

        HashSet<Object> set = new HashSet<Object>(Arrays.asList(array));

        return buildInClause(colName, set);
    }

    /**
     * 将Set<Object>中的项目转换成以逗号分隔的内容.
     *
     * @param set Set<String>
     * @return 类似如:"4,5,33,43,'a' "的字符串
     */
    private static String toString(Set<Object> set) {

        StringBuffer buff = new StringBuffer();
        for (Iterator<Object> it = set.iterator(); it.hasNext(); ) {
            Object obj = it.next();

            // 如果是字符或字符串型, 则转出时需要在元素两边加上单引号'
            if (obj instanceof String || obj instanceof Character) {
                String s = obj.toString();
                s = s.replace("\'", "''");// 将条件中的单引号替换成丙个单引号

                if (!s.trim().equals("")) {
                    buff.append("\'");
                    buff.append(s.trim());
                    buff.append("\',");
                }
            } else {
                buff.append(obj.toString());
                buff.append(",");
            }
        }
        if (buff.length() > 0) {
            buff.deleteCharAt(buff.length() - 1);
        }

        return buff.toString();
    }

}
