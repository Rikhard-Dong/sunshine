package com.hfmes.sunshine.cache;

import com.hfmes.sunshine.dao.DevcDao;
import com.hfmes.sunshine.domain.Devc;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/16 17:23
 * <p>
 * 记录设备对应生产数量
 * key device id value count 统计生产数量
 */
@Slf4j
public class CountNumsCache {
    private static Map<Integer, Integer> cache;


    private CountNumsCache() {
    }

    public static void init(DevcDao devcDao) {
        log.info("初始化统计数量缓存...");

        cache = new ConcurrentHashMap<>();

        List<Devc> devcList = devcDao.findAll();
        if (devcList.size() > 0) {
            for (Devc devc : devcList) {
                cache.put(devc.getDeviceId(), 0);
            }
        } else {
            log.warn("警告 --> 初始化countNumsCache, 当前没有设备数据");
        }
    }

    public static void put(Integer devcId, Integer counts) {
        cache.put(devcId, counts);
    }

    public static Integer get(Integer devcId) {
        return cache.get(devcId);
    }

    public static void remove(Integer devcId) {
        cache.remove(devcId);
    }

}
