/**
 * Baijiahulian.com Inc. Copyright (c) 2014-2015 All Rights Reserved.
 */

package shizuwei.test.java;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import shizuwei.test.db.po.User;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * @title JasonUsage
 * @desc TODO
 * @author shizuwei
 * @date 2015年11月10日
 * @version 1.0
 */

public class JacksonUsage {

    private static final Logger log = LoggerFactory.getLogger(JacksonUsage.class);

    private void testJacksonUtil() throws Exception {
        User user = new User();
        user.setAge(12);
        user.setAreaId(1L);
        user.setCreateTime(new Date());
        user.setId(11L);
        user.setMobile(null);
        user.setName("shizuwei");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);
        log.debug("json = {}", json);
        // 读取JSON，没有的属性为null,null转为null
        json =
            "{\"id\":null,\"name\":\"shizuwei\",\"createTime\":1447139255127,\"areaId\":1,\"mobile\":\"18675676767\"}";
        // null转为null,对于不存在的属性不会报错，转为null
        User user2 = mapper.readValue(json, User.class);
        log.debug("user2 = {}", user2);

        // JSON包含Bean中没有的属性，异常
        try {
            json =
                "{\"id\":11,\"name\":\"shizuwei\",\"age\":12,\"createTime\":1447139255127,\"areaId\":1,\"mobile\":\"18675676767\",\"xxx\":111}";
            // xxx属性没有在User中定义，抛出异常
            User user3 = mapper.readValue(json, User.class);
            log.debug("user3 = {}", user3);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        Map<String, Object> userMap = Maps.newHashMap();
        userMap.put("int", 1);
        userMap.put("string", "string");
        userMap.put(Integer.valueOf(1111).toString(), "string");
        log.debug("map = {}", mapper.writeValueAsString(userMap));

        // JAVA集合转换为JSON数组
        Collection<User> users = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            User u = new User();
            u.setAge(12);
            u.setAreaId(1L);
            u.setCreateTime(new Date());
            u.setId(11L);
            u.setMobile(null);
            u.setName("shizuwei");
            users.add(u);
        }
        String us = mapper.writeValueAsString(users);
        log.debug("json = {}", us);

        // 如何把Json数组转化为Java集合类
        String jus =
            "[{\"id\":11,\"name\":\"shizuwei\",\"age\":12,\"createTime\":1447213308695,\"areaId\":1,\"mobile\":null},"
                + "{\"id\":11,\"name\":\"shizuwei\",\"age\":12,\"createTime\":1447213308695,\"areaId\":1,\"mobile\":null}]";
        @SuppressWarnings("unchecked")
        Collection<User> us2 =
            mapper.readValue(jus, mapper.getTypeFactory().constructCollectionType(Collection.class, User.class));
        for (User uu : us2) {
            log.debug("{}{}{}", uu.getAge(), uu.getAreaId(), uu.getName());
        }

        log.debug("us2={}", us2);
    }

    public static void main(String[] args) throws Exception {
        JacksonUsage usage = new JacksonUsage();
        usage.testJacksonUtil();
    }
}
