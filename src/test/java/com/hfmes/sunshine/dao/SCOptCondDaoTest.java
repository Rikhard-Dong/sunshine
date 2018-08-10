package com.hfmes.sunshine.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/10 10:53
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class SCOptCondDaoTest {


    @Autowired
    private SCOptCondDao optCondDao;

    @Test
    public void getValueByOptIdAndConditionId() {
        Boolean result = optCondDao.getValueByOptIdAndConditionId(1, 1);
        assertNotNull(result);
        assertFalse(result);
    }
}