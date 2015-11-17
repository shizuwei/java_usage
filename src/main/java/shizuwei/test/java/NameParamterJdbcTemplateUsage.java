/**
 * Baijiahulian.com Inc. Copyright (c) 2014-2015 All Rights Reserved.
 */
package shizuwei.test.java;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import shizuwei.test.db.po.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NameParamterJdbcTemplateUsage {

    private static final Logger logger = LoggerFactory.getLogger(NameParamterJdbcTemplateUsage.class);
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public NameParamterJdbcTemplateUsage() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf8");
        dataSource.setPassword("");
        dataSource.setUser("mysql");
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    // 测试三个excute方法
    public void testExecute() {
        String sql = "select * from user where id = :id";
        Map<String, Object> params = Maps.newHashMap();
        params.put("id", 1);
        User user1 =
            namedParameterJdbcTemplate.execute(sql, new MapSqlParameterSource(params),
                new PreparedStatementCallback<User>() {

                    @Override
                    public User doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                        // sql已经设置好，并且参数已经替换完毕，可以直接使用
                        ResultSet rs = ps.executeQuery();
                        // 注意next()一下,指向第一列
                        rs.next();
                        return buildUser(rs);

                    }
                });
        User user2 = namedParameterJdbcTemplate.execute(sql, params, new PreparedStatementCallback<User>() {

            @Override
            public User doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {

                ResultSet rs = ps.executeQuery();
                rs.next();
                // 注意next()一下,指向第一列
                return buildUser(rs);

            }
        });

        List<User> users =
            namedParameterJdbcTemplate.execute("select * from user", new PreparedStatementCallback<List<User>>() {
                @Override
                public List<User> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {

                    ResultSet rs = ps.executeQuery();
                    List<User> users = Lists.newArrayList();
                    // 注意next()一下,指向第一列
                    while (rs.next()) {
                        users.add(buildUser(rs));
                    }
                    return users;
                }
            });

        return;

    }

    public void testQuery() {
        String sql = "select * from user";
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("id", 1);
        // test 1 针对整个结果集
        namedParameterJdbcTemplate.query(sql, new ResultSetExtractor<List<User>>() {

            @Override
            public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {

                List<User> users = Lists.newArrayList();
                // 注意next()一下,指向第一列
                while (rs.next()) {
                    users.add(buildUser(rs));
                }
                return users;

            }
        });

        // test2 只针对row 不用调用next()
        final List<User> users = Lists.newArrayList();
        namedParameterJdbcTemplate.query(sql, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                // 获取一个数据row
                users.add(buildUser(rs));
            }
        });

        // test3 每次只处理一行，最后形成结果集返回
        List<User> users2 = namedParameterJdbcTemplate.query(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                logger.debug("rowNum = {}", rowNum);
                return buildUser(rs);
            }
        });

        // namedParameterJdbcTemplate.query(sql, paramMap, rse);
        // namedParameterJdbcTemplate.query(sql, paramMap, rch);
        // namedParameterJdbcTemplate.query(sql, paramMap, rowMapper);
        // 以上三个和1，2，3是一样的，只是可以通过paramMap添加参数

        // namedParameterJdbcTemplate.query(sql, paramSource, rse)
        // namedParameterJdbcTemplate.query(sql, paramSource, rch);
        // 注意SqlParameterSource的两个实现：MapSqlParameterSource :new MapSqlParameterSource(Maps.newHashMap())
        // BeanPropertySqlParameterSource 直接使用bean
        User user4 = new User();
        user4.setId(1L);
        List<User> listUser =
            namedParameterJdbcTemplate.query("select * from user where id = :id", new BeanPropertySqlParameterSource(
                user4), new RowMapper<User>() {

                @Override
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {

                    // 这个和test3一样
                    return buildUser(rs);

                }

            });

        // test 10,都过期了，使用forObject吧

        // Integer count1 = namedParameterJdbcTemplate.queryForInt("select count(1) from user where id = :id",
        // paramMap);

        // 这个函数，使用bean作为参数，并且返回值的填写也使用bean
        List<User> users10 =
            namedParameterJdbcTemplate.queryForList(sql, new BeanPropertySqlParameterSource(user4), User.class);// 这个方法牛逼

        Integer count2 =
            namedParameterJdbcTemplate.queryForObject("select count(1) from user where id = :id", paramMap,
                Integer.class);

        List<Long> ids =
            namedParameterJdbcTemplate.queryForList(sql, new BeanPropertySqlParameterSource(user4), Long.class);

        return;

    }

    public void testUpdate() {
        String sql = "insert into user (name,age,area_id,mobile) values (:name,:age,:areaId,:mobile)";
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("name", "哈哈");
        paramMap.put("age", 11);
        paramMap.put("areaId", 123);
        paramMap.put("mobile", "18672979850");
        // int count = namedParameterJdbcTemplate.update(sql, paramMap);
        User user = new User();
        user.setAge(11);
        user.setAreaId(110L);
        user.setMobile("15997666334");
        user.setName("larry");
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();// 获取自动生成的id
        int count =
            namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(user), generatedKeyHolder);
        logger.debug("count = {}, generatedKeyHolder = {}", count, generatedKeyHolder.getKey());
    }

    @SuppressWarnings("unchecked")
    public void testBatch() {
        String sql = "insert into user (name,age,area_id,mobile) values (:name,:age,:areaId,:mobile)";
        @SuppressWarnings("rawtypes")
        Map[] batchValues = new HashMap[10];
        for (int i = 0; i < batchValues.length; i++) {
            Map v = new HashMap();
            batchValues[i] = v;
            batchValues[i].put("name", "哈哈" + i);
            batchValues[i].put("age", 11 + i);
            batchValues[i].put("areaId", 123 + i);
            batchValues[i].put("mobile", "1867297985" + i);
        }
        // test1
        int[] counts = namedParameterJdbcTemplate.batchUpdate(sql, batchValues);
        logger.debug("counts = {}", counts);

        // test2
        SqlParameterSource[] params = new SqlParameterSource[10];
        for (int i = 0; i < params.length; i++) {
            User user = new User();
            user.setAge(11 + i);
            user.setAreaId(110L + i);
            user.setMobile("1599766633" + i);
            user.setName("larry" + i);
            params[i] = new BeanPropertySqlParameterSource(user);
        }
        int[] c2 = namedParameterJdbcTemplate.batchUpdate(sql, params);

        logger.debug("counts = {}", c2);

    }

    private User buildUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong(1));
        user.setName(rs.getString(2));
        user.setAge(rs.getInt(3));
        user.setCreateTime(rs.getDate(4));
        user.setMobile(rs.getString(5));
        return user;
    }

    public static void main(String[] args) {
        NameParamterJdbcTemplateUsage usage = new NameParamterJdbcTemplateUsage();
        usage.testExecute();
        usage.testQuery();
        usage.testUpdate();
        usage.testBatch();
    }
}
