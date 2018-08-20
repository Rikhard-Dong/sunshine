package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.Devc;
import com.hfmes.sunshine.domain.Task;
import com.hfmes.sunshine.utils.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
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

    @Autowired
    private TaskDao  taskDao;


    @Test
    public void findByTaskId() {
        Task task = devcMap.get(2).getTask();
        String result = JacksonUtils.toJSon(task);
        log.debug("task json --> {}", result);

        log.debug("task --> {}", task);
        log.debug("task devOpId --> {}, devOp --> {}", task.getDevOpId(), task.getDevOp());
        log.debug("task mldOpId --> {}, mldOp --> {}", task.getMldOpId(), task.getMldOp());
        log.debug("task mldDtlId --> {}, mldDtl --> {}", task.getMldDtlId(), task.getMldDtl());

        result = JacksonUtils.toJSon(task);
        log.debug("task json --> {}", result);
    }

    @Test
    public void findByStatusIsST00ByDevcId() {
        List<Task> tasks = taskDao.findByStatusIsST00ByDevcId(2);
        log.info("tasks -> {}", tasks);
    }
}