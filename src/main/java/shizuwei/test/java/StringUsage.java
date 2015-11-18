/**
 * Baijiahulian.com Inc. Copyright (c) 2014-2015 All Rights Reserved.
 */

package shizuwei.test.java;

import com.google.common.collect.Lists;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @title StringUsage
 * @desc TODO
 * @author shizuwei
 * @date 2015年11月17日
 * @version 1.0
 */

public class StringUsage {

    private static final Logger log = LoggerFactory.getLogger(StringUsage.class);

    static class Pet extends Animal {
    }

    static class Cat extends Pet {
    }

    static class Animal {
    }

    static class Person extends Animal {
    }

    public static void main(String[] args) throws Throwable {
        List<Class<? extends Pet>> list = Lists.newArrayList();
        list.add(Cat.class);

        list = Collections.unmodifiableList(list);

        Arrays.asList();

        boolean b = Integer.class.isInstance(Integer.valueOf(1));

        log.info("b = {}", b);

    }
}
