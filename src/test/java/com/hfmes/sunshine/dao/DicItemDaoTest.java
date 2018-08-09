package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.DicItem;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 8:18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DicItemDaoTest {

    @Autowired
    private DicItemDao dicItemDao;

    @Test
    public void findByCode() {
        DicItem dicItem = dicItemDao.findByCode("SM00");
        assertNotNull(dicItem);
        log.debug("dicItem --> {}", dicItem);
    }

    @Test
    public void findById() {
        DicItem dicItem = dicItemDao.findById(1);
        assertNotNull(dicItem);
        log.debug("dicItem --> {}", dicItem);
    }
}