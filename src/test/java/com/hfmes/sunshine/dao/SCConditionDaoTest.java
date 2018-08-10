package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.SCCondition;
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
 * @date 2018/8/9 18:40
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class SCConditionDaoTest {

    @Autowired
    private SCConditionDao conditionDao;

    @Test
    public void findByOpId() {
        List<SCCondition> list = conditionDao.findByOpId(1);
        assertNotNull(list);
        assertNotEquals(list.size(), 0);
        log.debug("list --> {}", list);
    }
}