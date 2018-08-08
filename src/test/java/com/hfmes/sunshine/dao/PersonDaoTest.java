package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.Person;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 21:30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PersonDaoTest {

    @Autowired
    private PersonDao personDao;

    @Test
    public void findByPersonId() {
        Person person = personDao.findByPersonId(3);
        assertNotNull(person);
        log.debug("person --> {}", person);
    }

    @Test
    public void findByCardNo() {
        Person person = personDao.findByCardNo("4D7E5695");
        assertNotNull(person);
        log.debug("person --> {}", person);
    }
}