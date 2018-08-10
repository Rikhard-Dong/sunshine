package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.Devc;
import com.hfmes.sunshine.enums.DeviceStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 22:14
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DevcDaoTest {

    @Autowired
    private DevcDao deviceDao;

    @Test
    public void findAll() {
        List<Devc> a = deviceDao.findAll();
        log.debug("{}", a);
    }

    @Test
    public void findByDeviceId() {

        Devc devc = deviceDao.findByDeviceId(2);
        log.debug("devc --> {}", devc);
        log.debug("devc dept --> {}", devc.getDept());
        log.debug("devc task --> {}", devc.getTask());

    }

    @Test
    public void getStatusByDeviceId() {

        DeviceStatus status = deviceDao.getStatusByDeviceId(2);
        log.debug("status --> {}", status);
    }
}