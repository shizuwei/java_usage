/**
 * Baijiahulian.com Inc. Copyright (c) 2014-2015 All Rights Reserved.
 */

package shizuwei.test.java;

import shizuwei.test.db.PO;

/**
 * @title Test
 * @desc TODO
 * @author shizuwei
 * @date 2015年10月25日
 * @version 1.0
 */

public class Test {

    private String msg = "Test msg!";

    class Inner {

        public void getOuterMsg() {
            System.out.println(Test.this.msg);
        }
    }

    public static void main(String[] args) throws Throwable {

        /*
         * Test test = new Test(); Test.Inner inner = test.new Inner(); inner.getOuterMsg();
         * 
         * throw new Exception().fillInStackTrace();
         */

        PO po = new PO();
        po.getName().trim();
        // Long count = po.getCount().longValue();
        Integer i;
        Long count = Long.valueOf(po.getCount());

    }
}
