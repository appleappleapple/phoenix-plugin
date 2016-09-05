package com.github.phoenix.plugin.bulider.test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Test<T> {

    private String name;

    private int age;

    private List<String> names;

    private String[] lives;

    private Boolean[] bs;

    private A<String> aStr;

    private A<Integer> aint;

    static class A<T> {

        T t;

        public void test() {
            System.out.println(t);
        }
    }

    static class B<T> {

        T t;

        public B(T t) {
            this.t = t;
        }

        public void test() {
            System.out.println(t);
        }
    }

    public static void main(String[] args) throws Throwable {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        for (Field f : Test.class.getDeclaredFields()) {
            System.out.println(f.getName() + "." + f.getType());
            classes.add(f.getType());
        }
        System.out.println(classes);
        // System.out.println(new String[0].getClass());
        // System.out.println(new Boolean[0].getClass());
//
//        Test<String> t = new Test<String>();
        // t.testGeneric();

        // Class<?> c = A.class;
        // Object o = c.newInstance();
        // Method m = c.getDeclaredMethod("test");
        // m.invoke(o);
        //
        // c = B.class;
        // Constructor con = c.getDeclaredConstructor(new Class[] { Object.class }); // 用Object.class代替T
        // o = con.newInstance(new Object[] { "bbb" });
        // m = c.getDeclaredMethod("test");
        // m.invoke(o);
        //
        // List<String > list = new ArrayList<String>();
        // Class type = ClassUtil.getSuperClassGenricType(list.getClass(), 0);
        // System.err.println("g:" + type);
    }

    private void testGeneric() {

        try {
            Map<String, Double> map = new HashMap<String, Double>();
            System.out.println(Test.class.getMethod("getNames").toGenericString());
            System.out.println(Test.class.getDeclaredField("names").toGenericString());
            System.out.println(new Test<String>().getClass().getGenericSuperclass());
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
            // TODO Auto-generated catch block
        }
        catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
