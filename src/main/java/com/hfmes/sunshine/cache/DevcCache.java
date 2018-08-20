package com.hfmes.sunshine.cache;

import com.hfmes.sunshine.dao.DevcDao;
import com.hfmes.sunshine.domain.Devc;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/16 10:49
 */
@Slf4j
public class DevcCache {
    private static Map<Integer, Devc> cache;

    private DevcCache() {
    }

    /**
     * 初始化设备缓存信息
     *
     * @param devcDao
     */
    public static void init(DevcDao devcDao) {
        log.info("初始化设备信息缓存...");
        cache = new ConcurrentHashMap<>();

        List<Devc> devcList = devcDao.findAll();
        if (devcList != null && devcList.size() != 0) {
            for (Devc devc : devcList) {
                cache.put(devc.getDeviceId(), devc);
            }
        } else {
            log.warn("警告 --> 当前数据库中没有设备信息");
        }
    }


    public static void put(Integer devcId, Devc devc) {
        cache.put(devcId, devc);
    }

    public static Devc get(Integer devcId) {
        return cache.get(devcId);
    }

    public static void remove(Integer devcId) {
        cache.remove(devcId);
    }
}
