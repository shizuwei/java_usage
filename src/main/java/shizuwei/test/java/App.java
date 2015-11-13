package shizuwei.test.java;

import com.google.common.collect.Maps;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import shizuwei.test.db.po.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private static final String sql = "select * from user where id = :id";

    public static void main(String[] args) {

        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("applicationContext.xml"));
        // MysqlDataSource dataSource = (MysqlDataSource) factory.getBean("dataSource");
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/test");
        dataSource.setPassword("");
        dataSource.setUser("mysql");

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        Map<String, Object> params = Maps.newHashMap();
        params.put("id", 1);
        User user =
            namedParameterJdbcTemplate.execute(sql, new MapSqlParameterSource(params),
                new PreparedStatementCallback<User>() {

                    @Override
                    public User doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {

                        ResultSet rs = ps.executeQuery();
                        rs.next();
                        User user = new User();

                        user.setId(rs.getLong(1));
                        user.setName(rs.getString(2));
                        user.setAge(rs.getInt(3));
                        user.setCreateTime(rs.getDate(4));
                        user.setMobile(rs.getString(5));

                        return user;

                    }
                });

        logger.info("{}", user);
    }
}
