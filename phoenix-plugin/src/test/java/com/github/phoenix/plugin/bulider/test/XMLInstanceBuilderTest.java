package com.github.phoenix.plugin.bulider.test;

import com.github.phoenix.plugin.bulider.XMLInstanceBuilder;
import com.github.phoenix.plugin.client.test.Test;

public class XMLInstanceBuilderTest {
	
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        XMLInstanceBuilder b = new XMLInstanceBuilder();
        String file = b.getClass().getClassLoader().getResource("com/github/phoenix/plugin/bulider/test/Test.xml").getFile();
        Test<String> t = b.buildInstanceFromFile(new Test<String>().getClass(), file);
        System.out.println("t:" + t.toString());
    }

}
