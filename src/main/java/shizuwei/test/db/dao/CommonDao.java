/**
 * Baijiahulian.com Inc. Copyright (c) 2014-2015 All Rights Reserved.
 */

package shizuwei.test.db.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

public class CommonDao extends NamedParameterJdbcDaoSupport {

    public void test() {
        this.getNamedParameterJdbcTemplate();
    }

}
