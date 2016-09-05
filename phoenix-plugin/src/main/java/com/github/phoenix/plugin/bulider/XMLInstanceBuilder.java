package com.github.phoenix.plugin.bulider;


import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.phoenix.plugin.exception.InstanceBuildException;
import com.github.phoenix.plugin.utils.ClassUtil;


public class XMLInstanceBuilder implements FileInstanceBuilder {

    Logger logger = LoggerFactory.getLogger(XMLInstanceBuilder.class);

    public <T> T buildInstanceFromText(Class<T> type, String text) {
        try {
            Document document = DocumentHelper.parseText(text);
            Element rootE = document.getRootElement();
            T ret = this.buildInstance(type, rootE);
            logger.debug("finish read data.  root name:" + rootE.getName() + ">>" + ret);
            return ret;
        }
        catch (DocumentException e) {
            throw new InstanceBuildException(e);
        }
    }

    public <T> T buildInstanceFromFile(Class<T> type, String file) {
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(file);
            Element rootE = document.getRootElement();
            T ret = this.buildInstance(type, rootE);
            logger.debug("finish read data.  root name:" + rootE.getName() + ">>" + ret);
            return ret;
        }
        catch (DocumentException e) {
            throw new InstanceBuildException(e);
        }
    }
    
    public <T> T buildInstanceFromFile(Class<T> type, InputStream ips) {
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(ips);
            Element rootE = document.getRootElement();
            T ret = this.buildInstance(type, rootE);
            logger.debug("finish read data.  root name:" + rootE.getName() + ">>" + ret);
            return ret;
        }
        catch (DocumentException e) {
            throw new InstanceBuildException(e);
        }
    }

    public <T> T buildInstance(Class<T> type, Element element) {
        Map<String, String> fieldMap = new HashMap<String, String>();
        return (T) buildInstance(type, fieldMap, element);
    }

    @SuppressWarnings({ "unchecked", "unused" })
    public <T> T buildInstance(Class<T> type, Map<String, String> fieldMap, Element element) {
        T ret = null;
        if (fieldMap == null) {
            fieldMap = new HashMap<String, String>();
        }
        try {
            if (ClassUtil.isPriovateType(type)) {
            	Attribute valueAttr = element.attribute("value");
                Object valueObj =null;
                
                if (valueAttr != null) {
                    ret = (T) ClassUtil.getPrivateTypeValue(type, valueAttr.getText());
                }
                Attribute istrim = element.attribute("trim");
               if(istrim != null &&((String)istrim.getText()).toLowerCase().equals("false")){
            	   valueObj = element.getText();
               }else{
            	   valueObj = element.getTextTrim();
               }
                if (valueObj != null) {
                    ret = (T) ClassUtil.getPrivateTypeValue(type, (String) valueObj);
                }
                return ret;
            }

            ret = type.newInstance();
            for (Iterator<Attribute> attrIt = element.attributeIterator(); attrIt.hasNext();) {
                Attribute attrE = attrIt.next();
                ClassUtil.setObjectValue(ret, attrE.getName(), attrE.getValue());
            }
            for (Iterator<Element> eit = element.elementIterator(); eit.hasNext();) {
                Element _e = eit.next();
                String fieldName = fieldMap.get(_e.getPath());
                if (fieldName == null) {
                    fieldName = _e.getName();
                }
                Field field = type.getDeclaredField(fieldName);
                Class<?> fieldType = field.getType();
                if (field == null) {
                    throw new RuntimeException("field not found:" + _e.getName());
                }

                Object fieldValue = null;
                if (fieldType == List.class) {
                    fieldValue = buildListFieldInstance(field, fieldMap, _e);
                }
                else if (fieldType == Map.class) {
                    Map<String, String> _map = new HashMap<String, String>();
                    for (Iterator<Element> it = element.elementIterator(); it.hasNext();) {
                        Element _el = it.next();
                        _map.put(_el.attributeValue("key"), _el.attributeValue("value"));
                    }
                    fieldValue = _map;
                }
                else {
                    fieldValue = buildInstance(fieldType, fieldMap, _e);
                }

                ClassUtil.setObjectValue(ret, fieldName, fieldValue);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            throw new InstanceBuildException(e);
        }

        logger.debug("*****************" + element.getName() + "********************");
        logger.debug(ret.toString());
        logger.debug("*************************************");
        return ret;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private List<Object> buildListFieldInstance(Field field, Map<String, String> fieldMap, Element element) {
        Class<?> _type = null;
        try {
            String typeName = element.attribute("class").getValue();
            _type = Class.forName(typeName);
        }
        catch (Exception e) {
            _type = ClassUtil.getFieldGenricType(field, 0);
            if (_type == null) {
                throw new RuntimeException("no class name found for list item.");
            }
        }

        List ret = new ArrayList();

        for (Iterator<Element> eit = element.elementIterator(); eit.hasNext();) {
            Element _e = eit.next();
            Object _obj = buildInstance(_type, fieldMap, _e);
            ((List) ret).add(_obj);
        }
        return ret;
    }


    @SuppressWarnings({ "unused", "rawtypes" })
    private Class findClass(String name) {
        if ("String".equals(name)) {
            return String.class;
        }
        else if ("int".equals(name)) {
            return Integer.class;
        }
        else if ("double".equals(name)) {
            return Double.class;
        }
        else if ("Date".equals(name)) {
            return Date.class;
        }
        return null;
    }

    private String getAttribute(Element element, String attrName) {
        String ret = element.attributeValue(attrName);
        if (ret == null) {
            ret = element.getTextTrim();
            if (ret.equals(""))
                ret = null;
        }
        return ret;

    }

   
}
