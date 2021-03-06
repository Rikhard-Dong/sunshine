package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.MldDtl;
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
 * @date 2018/8/8 21:13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MldDtlDaoTest {

    @Autowired
    private MldDtlDao mldDtlDao;

    @Test
    public void findByMldDtlId() {
        MldDtl mldDtl = mldDtlDao.findByMldDtlId(1);
        assertNotNull(mldDtl);
        log.debug("mldDtl --> {}", mldDtl);
    }

    @Test
    public void findAll() {
        List<MldDtl> mldDtls = mldDtlDao.findAll();
        assertNotNull(mldDtls);
        assertNotEquals(mldDtls.size(), 0);
        log.debug("mlddtl list --> {}", mldDtls);
    }

    @Test
    public void getStatusByMldId() {
        String curState = mldDtlDao.getStatusByMldId(1);
        log.debug("cur state --> {}", curState);
    }
}