package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.DicType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 22:30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DicTypeDaoTest {

    @Autowired
    private DicTypeDao dicTypeDao;

    @Test
    public void findById() {
        DicType dicType = dicTypeDao.findById(1);
        assertNotNull(dicType);
        log.debug("dicType --> {}", dicType);
    }
}