package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.SCOption;
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
 * @date 2018/8/8 22:17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SCOptionDaoTest {

    @Autowired
    private SCOptionDao scOptionDao;

    @Test
    public void findBySCOptionId() {
    }

    @Test
    public void findByRoleId() {
        List<SCOption> scOptions = scOptionDao.findByRoleId(9);
        assertNotNull(scOptions);
        assertNotEquals(scOptions.size(), 0);

        log.debug("scOptions --> {}", scOptions);
    }

    @Test
    public void findByCardNoAndDeviceId() {
    }
}