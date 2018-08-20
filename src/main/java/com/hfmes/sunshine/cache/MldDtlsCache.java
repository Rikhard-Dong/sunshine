package com.hfmes.sunshine.cache;

import com.hfmes.sunshine.dao.MldDtlDao;
import com.hfmes.sunshine.domain.MldDtl;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/16 18:45
 * <p>
 * 存放模具缓存
 * key mldDtl id  value mldDtl
 */
@Slf4j
public class MldDtlsCache {

    private static Map<Integer, MldDtl> cache;

    private MldDtlsCache() {
    }

    public static void init(MldDtlDao mldDtlDao) {
        log.info("初始化模具信息缓存...");

        cache = new ConcurrentHashMap<>();
        List<MldDtl> mldDtls = mldDtlDao.findAll();
        if (mldDtls != null && mldDtls.size() > 0) {
            for (MldDtl mldDtl : mldDtls) {
                cache.put(mldDtl.getMldDtlId(), mldDtl);
            }
        } else {
            log.warn("initMldDtlMap 警告 --> 没有模具信息");
        }

    }

    public static void put(Integer mldDtlId, MldDtl mldDtl) {
        cache.put(mldDtlId, mldDtl);
    }

    public static MldDtl get(Integer mldDtlId) {
        return cache.get(mldDtlId);
    }

    public static void remove(Integer mldDtlId) {
        cache.remove(mldDtlId);
    }
}
