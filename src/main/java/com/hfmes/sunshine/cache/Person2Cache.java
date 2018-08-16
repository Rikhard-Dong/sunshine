package com.hfmes.sunshine.cache;

import com.hfmes.sunshine.dao.PersonDao;
import com.hfmes.sunshine.domain.Person;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/16 16:47
 * key person id  value perosn
 */
@Slf4j
public class Person2Cache {

    private static Map<Integer, Person> cache;

    public static void init(PersonDao personDao) {
        log.info("初始化person2缓存...");

        cache = new ConcurrentHashMap<>();
        List<Person> persons = personDao.findAll();
        if (persons != null && persons.size() > 0) {
            for (Person person : persons) {
                cache.put(person.getPersonId(), person);
            }
        } else {
            log.warn("初始化person2缓存警告--> 当前没有person数据");
        }
    }

    public static void put(Integer personId, Person person) {
        cache.put(personId, person);
    }

    public static Person get(Integer personId) {
        return cache.get(personId);
    }

    public static void remove(Integer personId) {
        cache.remove(personId);
    }
}
