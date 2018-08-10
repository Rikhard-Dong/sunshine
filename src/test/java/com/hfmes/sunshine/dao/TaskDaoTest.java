package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.Devc;
import com.hfmes.sunshine.domain.Task;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/10 14:02
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TaskDaoTest {

    @Autowired
    @Qualifier("devcs")
    private Map<Integer, Devc> devcMap;


    @Test
    public void findByTaskId() {
        Task task = devcMap.get(2).getTask();
        log.debug("task --> {}", task);
        log.debug("task devOpId --> {}, devOp --> {}", task.getDevOpId(), task.getDevOp());
        log.debug("task mldOpId --> {}, mldOp --> {}", task.getMldOpId(), task.getMldOp());
    }
}