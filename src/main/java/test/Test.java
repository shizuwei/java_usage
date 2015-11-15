/**
 * Baijiahulian.com Inc. Copyright (c) 2014-2015 All Rights Reserved.
 */

package test;

import java.util.Map;

/**
 * @title Test
 * @desc TODO
 * @author shizuwei
 * @date 2015年11月14日
 * @version 1.0
 */

public class Test {

    public static void main(String[] args) {
        for (Map.Entry entry : System.getenv().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

}
