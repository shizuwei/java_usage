/**
 * Baijiahulian.com Inc. Copyright (c) 2014-2015 All Rights Reserved.
 */

package shizuwei.test.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @title RegxUsage
 * @desc TODO
 * @author shizuwei
 * @date 2015年11月13日
 * @version 1.0
 */

public class RegxUsage {
    private static final Logger log = LoggerFactory.getLogger(RegxUsage.class);

    public void testMatch() {
        log.debug("xxx");
    }

    public static void main(String[] args) {
        RegxUsage regxUsage = new RegxUsage();
        regxUsage.testMatch();
    }

}
