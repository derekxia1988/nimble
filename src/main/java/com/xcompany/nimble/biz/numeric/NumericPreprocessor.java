/*
 * Copyright (c) 2022.  Topjoy all rights reserved.
 */

package com.xcompany.nimble.biz.numeric;

/**
 * 预处理策划数值接口，所有对策划数值进行了预处理的类都实现此接口。<p>
 * 预处理策划数值必须遵循以下规则：<p>
 * 1. 预处理数据在修改成员变量时使用赋值操作，即先构建一个临时变量存储预处理的数据，最后直接赋值给成员变量。<p>
 * 2. 将提供的方法定义成public，因为有AOP操作，详见{@link NumericsAspect}。<p>
 *
 * @author xiadong
 * @since 2022/01/12
 **/
public interface NumericPreprocessor {
    /**
     * 预处理策划数值
     */
    void process();

    /**
     * 热更
     */
    default void reload() {
        process();
    }
}


