package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.StatusData;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/13 12:59
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StatusDataDaoTest {

    @Autowired
    private StatusDataDao statusDataDao;

    @Test
    public void insertOne() {

        StatusData data = new StatusData();
        Integer i = statusDataDao.insertOne(data);
        assertEquals(i, new Integer(1));
        log.debug("data id --> {}", data.getStatusDataId());
    }
}