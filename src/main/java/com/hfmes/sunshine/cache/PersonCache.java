package com.hfmes.sunshine.cache;

import com.hfmes.sunshine.dao.PersonDao;
import com.hfmes.sunshine.domain.Person;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/16 16:37
 * <p>
 * key 卡号 value perosn
 * 只取卡号不为空的数据
 */
@Slf4j
public class PersonCache {

    private static Map<String, Person> cache;

    public static void init(PersonDao personDao) {
        log.info("初始化person缓存...");
        cache = new ConcurrentHashMap<>();

        List<Person> personList = personDao.findAll();
        if (personList != null && personList.size() != 0) {
            for (Person person : personList) {
                // 对卡号进行判断, 卡号不能为空且不能为null
                if (StringUtils.isNotEmpty(StringUtils.trim(person.getCardNo()))) {
                    cache.put(person.getCardNo(), person);
                }
            }
        } else {
            log.warn("警告 --> 当前数据库中没有人员信息");
            return;
        }

        if (cache.size() <= 0) {
            log.warn("警告 --> 数据库中没有符合的人员信息");
        }

    }

    public static void put(String cardNo, Person person) {
        cache.put(cardNo, person);
    }

    public static Person get(String cardNo) {
        return cache.get(cardNo);
    }

    public static void remove(String cardNo) {
        cache.remove(cardNo);
    }

    public static Map<String, Person> getMap() {
        return cache;
    }
}
