package com.hfmes.sunshine.cache;

import com.hfmes.sunshine.dao.SCOptionDao;
import com.hfmes.sunshine.domain.SCOption;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/16 19:01
 * 操作名称缓存
 * key optionId value option name
 */
@Slf4j
public class OptionsCache {
    private static Map<Integer, String> cache;

    private OptionsCache() {

    }

    public static void init(SCOptionDao optionDao) {
        log.info("初始化options缓存...");
        cache = new ConcurrentHashMap<>();

        List<SCOption> options = optionDao.findAll();
        if (options != null && options.size() > 0) {
            for (SCOption option : options) {
                cache.put(option.getScOptionId(), option.getOptName());
            }
        } else {
            log.warn("初始话options缓存警告 --> 没有options信息");
        }

    }

    public static void put(Integer optionId, String optionName) {
        cache.put(optionId, optionName);
    }

    public static String get(Integer optionId) {
        return cache.get(optionId);
    }

    public static void remove(Integer optionId) {
        cache.get(optionId);
    }
}
