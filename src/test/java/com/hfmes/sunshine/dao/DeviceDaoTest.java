package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.Devc;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 20:42
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DeviceDaoTest {

    @Autowired
    private DeviceDao deviceDao;

    @Test
    public void findByDeviceId() {

        Devc device = deviceDao.findByDeviceId(2);
        assertNotNull(device);
        log.debug("device --> {}", device);
    }
}