/**
 * Baijiahulian.com Inc. Copyright (c) 2014-2015 All Rights Reserved.
 */

package test;

import com.google.common.base.Preconditions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * @title Test
 * @desc TODO
 * @author shizuwei
 * @date 2015年10月31日
 * @version 1.0
 */

public class TestNull {
    private static final Logger log = LoggerFactory.getLogger(TestNull.class);

    private String str;

    public static class Bean {

    }

    public void testNull() {

    }

    public void testString() {

        // 字符串比较，注意判空
        if (str.equals("literal")) {

            log.info("not good.");
        }

        if (str != null && str.equals("literal")) {
            log.info("good.");
        }

        if ("literal".equals(str)) {
            log.info("good.");
        }

        // 字符串判空
        StringUtils.isEmpty(str);

    }

    public <T> void testPamameter(Integer id, String name, List<T> list) {

        Preconditions.checkNotNull(name, "name may not be null");
        Preconditions.checkArgument(id > 0, "id must > 0");
        if (list.isEmpty()) {
            //
        }
    }

    // 异常时不要返回null
    public List<Integer> getCollection() {
        List<Integer> list = null;
        try {
            // do something
        } catch (Exception ex) {
            return Collections.emptyList();
        }

        return list;
    }

    // 返回单个Bean
    public Bean getUniqueBean(Long id) {
        Bean bean = null;
        try {
            // do something
        } catch (Exception ex) {
            return null;
        }

        return bean;
    }

    public static void main() {
        Long id = 1L;
        TestNull test = new TestNull();

        // 检查null
        Bean bean = test.getUniqueBean(id);
        if (bean != null) {

        }

    }
}
