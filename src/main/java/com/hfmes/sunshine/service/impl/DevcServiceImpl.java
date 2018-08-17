package com.hfmes.sunshine.service.impl;

import com.hfmes.sunshine.cache.DevcCache;
import com.hfmes.sunshine.dao.DevcDao;
import com.hfmes.sunshine.domain.Devc;
import com.hfmes.sunshine.service.DevcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DevcServiceImpl implements DevcService {

    private final DevcDao devcDao;

    @Autowired
    public DevcServiceImpl(DevcDao devcDao) {
        this.devcDao = devcDao;
    }

    /**
     * 更新设备对应的设备信息
     *
     * @param devcId
     * @return
     */
    @Override
    public Devc updateDevcFromSql(int devcId) {
        // TODO 影响生产计数, 改进
        Devc devc = devcDao.findByDeviceId(devcId);
        DevcCache.put(devcId, devc);
        return devc;
    }


}
