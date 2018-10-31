/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xiaoerzuche.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Groovy对象的成员变量装载
 * @author Nick C
 */
public class FieldWireUtil {

    static final Logger LOG = LoggerFactory.getLogger(FieldWireUtil.class);

    /**
     * 设置类成员属性
     *
     * @param target
     * @param fieldName
     * @param fieldValue
     */
    public static void wireField(Object target, String fieldName, Object fieldValue) {
        Class<?> clazz = target.getClass();
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, fieldValue);
        } catch (Exception e) {
            LOG.error("wireField" ,e) ;
        }
    }

    /**
     * 设置类成员属性
     *
     * @param target
     * @param fieldName
     * @param fieldValue
     */
    public static void wireField(Object target, Map<String, Object> fieldMap) {
        Class<?> clazz = target.getClass();

        // 声明的所有成员属性
        Field[] initFiles = clazz.getDeclaredFields();
        for (Field field : initFiles) {
            int modifier = field.getModifiers();
            if (Modifier.isFinal(modifier) || Modifier.isStatic(modifier)) {
                continue;
            } else {
                Class<?> type = field.getType();
                // 常用基本类型
                if (type.isPrimitive() || type == List.class || type == Map.class) {
                    continue;
                }
            }

            Object fieldValue = fieldMap.get(field.getName());
            if (fieldValue == null) {
                continue;
            }

            field.setAccessible(true);
            try {
                field.set(target, fieldValue);
            } catch (Exception e) {
                LOG.error("wireField" ,e) ;
                throw new RuntimeException("属性:" + field.getName() + "初始化失败<" + e.getMessage() + ">");
            }
        }
    }
}
