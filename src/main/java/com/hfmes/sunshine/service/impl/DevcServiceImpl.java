package com.hfmes.sunshine.service.impl;

import com.hfmes.sunshine.dao.DevcDao;
import com.hfmes.sunshine.domain.Devc;
import com.hfmes.sunshine.service.DevcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DevcServiceImpl implements DevcService {

    private final DevcDao devcDao;
    private final Map<Integer, Devc> devcMap;

    @Autowired
    public DevcServiceImpl(DevcDao devcDao,
                           @Qualifier("devcs") Map<Integer, Devc> devcMap) {
        this.devcDao = devcDao;
        this.devcMap = devcMap;
    }

    /**
     * 更新设备对应的设备信息
     *
     * @param devcId
     * @return
     */
    @Override
    public Devc updateDevcFromSql(int devcId) {
        Devc devc = devcDao.findByDeviceId(devcId);
        devcMap.put(devcId, devc);
        return devc;
    }


}
