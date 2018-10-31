package com.xiaoerzuche.common.core.annotation;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 安全限制注解
 * @author chenchen
 * 2015年11月09日
 */
@Target({ METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SingleEntryLimit {

    /**
     * 传入singleEntryLimit，支持${0}..${9}占位符替换方法参数
     * @return 
     */
	String lockKey();
}