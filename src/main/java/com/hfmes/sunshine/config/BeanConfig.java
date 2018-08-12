package com.hfmes.sunshine.config;

import com.hfmes.sunshine.dao.DevcDao;
import com.hfmes.sunshine.dao.MldDtlDao;
import com.hfmes.sunshine.dao.PersonDao;
import com.hfmes.sunshine.dao.TaskDao;
import com.hfmes.sunshine.domain.Devc;
import com.hfmes.sunshine.domain.MldDtl;
import com.hfmes.sunshine.domain.Person;
import com.hfmes.sunshine.domain.Task;
import com.hfmes.sunshine.enums.DeviceEvents;
import com.hfmes.sunshine.enums.DeviceStatus;
import com.hfmes.sunshine.enums.MouldEvents;
import com.hfmes.sunshine.enums.MouldStatus;
import com.hfmes.sunshine.utils.StateMachineUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 8:42
 */
@Configuration
@Slf4j
public class BeanConfig {

    private final StateMachineFactory<DeviceStatus, DeviceEvents> deviceStateMachineFactory;

    private final StateMachineFactory<MouldStatus, MouldEvents> mouldStateMachineFactory;

    private final DevcDao deviceDao;

    private final PersonDao personDao;

    private final MldDtlDao mldDtlDao;

    private final TaskDao taskDao;

    @Autowired
    public BeanConfig(StateMachineFactory<DeviceStatus, DeviceEvents> deviceStateMachineFactory,
                      StateMachineFactory<MouldStatus, MouldEvents> mouldStateMachineFactory,
                      DevcDao deviceDao,
                      PersonDao personDao,
                      MldDtlDao mldDtlDao,
                      TaskDao taskDao) {
        this.deviceStateMachineFactory = deviceStateMachineFactory;
        this.mouldStateMachineFactory = mouldStateMachineFactory;
        this.deviceDao = deviceDao;
        this.personDao = personDao;
        this.mldDtlDao = mldDtlDao;
        this.taskDao = taskDao;
    }

    /* **********************************************************
     * 所有注入的bean
     ***********************************************************/

    /**
     * 注入所有设备map bean
     * key deviceId
     * value devc
     *
     * @return map of devices
     */
    @Bean(name = "devcs")
    public Map<Integer, Devc> devcs() {
        return initDevcs();
    }

    /**
     * 注入所有人员 map bean
     * key cardNo 卡号
     * value person
     *
     * @return map of persons
     */
    @Bean(name = "persons")
    public Map<String, Person> persons() {
        return initPersons();
    }

    /**
     * 存储所有设备状态机实例
     * key deviceId
     * value device statemachine instance
     *
     * @return map of device statemachine
     */
    @Bean(name = "deviceStateMachines")
    public Map<Integer, StateMachine<DeviceStatus, DeviceEvents>> deviceStateMachines() {
        return initDeviceStateMachines();
    }

    /**
     * @return
     */
    @Bean(name = "mouldStateMachines")
    public Map<Integer, StateMachine<MouldStatus, MouldEvents>> mouldStateMachines() {
        return initMouldStateMachines();
    }

    /**
     * key device id
     * value count 数量
     *
     * @return
     */
    @Bean(name = "countNums")
    public Map<Integer, Integer> countNums() {
        return initCountNums();
    }


    /**
     * key deviceId
     * value 设备对应的工单list
     *
     * @return
     */
    @Bean(name = "deviceTasks")
    public Map<Integer, List<Task>> deviceTasks() {
        return initDeviceTasks();
    }

    /**
     * 模具map
     * key 模具id
     * value 模具对象
     *
     * @return map of mldDtl
     */
    @Bean(name = "mldDtls")
    public Map<Integer, MldDtl> mldDtlMap() {
        return initMldDtlMap();
    }

    /**
     * 工单map, 只要任务状态为ST00分配状态的工单
     * key 工单id
     * value 工单对象
     *
     * @return map of task
     */
    @Bean(name = "tasks")
    public Map<Integer, Task> taskMap() {
        return initTaskMap();
    }


    /* **********************************************************
     * 注入bean实现过程
     ***********************************************************/

    /**
     * 初始化设备map
     *
     * @return map of devices
     */

    private Map<Integer, Devc> initDevcs() {
        Map<Integer, Devc> devcs = new ConcurrentHashMap<>();

        List<Devc> devcList = deviceDao.findAll();
        if (devcList != null && devcList.size() != 0) {
            for (Devc devc : devcList) {
                devcs.put(devc.getDeviceId(), devc);
            }
        } else {
            log.warn("警告 --> 当前数据库中没有设备信息");
        }

        return devcs;
    }

    /**
     * 初始化人员信息
     *
     * @return map of person
     */
    private Map<String, Person> initPersons() {
        Map<String, Person> persons = new ConcurrentHashMap<>();

        List<Person> personList = personDao.findAll();
        if (personList != null && personList.size() != 0) {
            for (Person person : personList) {
                // 对卡号进行判断, 卡号不能为空且不能为null
                if (StringUtils.isNotEmpty(StringUtils.trim(person.getCardNo()))) {
                    persons.put(person.getCardNo(), person);
                }
            }
        } else {
            log.warn("警告 --> 当前数据库中没有人员信息");
        }

        if (persons.size() <= 0) {
            log.warn("警告 --> 数据库中没有符合的人员信息");
        }

        return persons;
    }

    /**
     * 初始化所有设备状态机
     *
     * @return map of device state machines
     */
    private Map<Integer, StateMachine<DeviceStatus, DeviceEvents>> initDeviceStateMachines() {
        Map<Integer, StateMachine<DeviceStatus, DeviceEvents>> deviceStateMachines = new ConcurrentHashMap<>();

        if (deviceStateMachineFactory == null) {
            log.warn("device state machine factory can't be null");
            return null;
        }

        List<Devc> devcs = deviceDao.findAll();
        if (devcs != null && devcs.size() != 0) {
            for (Devc devc : devcs) {
                StateMachine<DeviceStatus, DeviceEvents> stateMachine = deviceStateMachineFactory.getStateMachine(devc.getTitle());
                // 从数据库恢复到指定状态
                stateMachine.start();
                if (devc.getStatus() != null) {
                    DeviceStatus status = DeviceStatus.valueOf(devc.getStatus());
                    log.debug("# status --> {}", status);
                    StateMachineUtils.setDeviceStateMachineState(stateMachine, status);
                }
                deviceStateMachines.put(devc.getDeviceId(), stateMachine);
            }
        } else {
            log.warn("警告 --> 当前数据库中没有设备信息");
        }

        return deviceStateMachines;
    }

    /**
     * 返回模具的状态机
     *
     * @return map of mould statemachies
     */
    private Map<Integer, StateMachine<MouldStatus, MouldEvents>> initMouldStateMachines() {
        Map<Integer, StateMachine<MouldStatus, MouldEvents>> mouldStateMachines = new ConcurrentHashMap<>();

        if (mouldStateMachineFactory == null) {
            log.warn("mould state machine factory can't be null!");
            return null;
        }
        List<MldDtl> mldDtls = mldDtlDao.findAll();
        if (mldDtls != null && mldDtls.size() != 0) {
            for (MldDtl mldDtl : mldDtls) {
                StateMachine<MouldStatus, MouldEvents> stateMachine = mouldStateMachineFactory.getStateMachine(mldDtl.getCode());
                // 从数据库中恢复模具的状态
                stateMachine.start();
                if (mldDtl.getStatus() != null) {
                    MouldStatus status = MouldStatus.valueOf(mldDtl.getStatus());
                    StateMachineUtils.setMouldStateMachineState(stateMachine, status);
                }

                mouldStateMachines.put(mldDtl.getMldDtlId(), stateMachine);
            }
        } else {
            log.warn("警告 --> 数据库中没有模具信息");
        }

        return mouldStateMachines;
    }


    /**
     * 生产计数
     *
     * @return
     */
    private Map<Integer, Integer> initCountNums() {
        List<Devc> devcList = deviceDao.findAll();
        Map<Integer, Integer> countNums = new ConcurrentHashMap<>();
        for (Devc devc : devcList) {
            countNums.put(devc.getDeviceId(), 0);
        }

        return countNums;
    }

    /**
     * @return
     */
    private Map<Integer, List<Task>> initDeviceTasks() {
        List<Task> tasks = taskDao.findByStatusIsST00();
        Map<Integer, List<Task>> result = new ConcurrentHashMap<>();

        for (Task task : tasks) {
            if (task.getDevcId() != null) {
                List<Task> tmp = result.computeIfAbsent(task.getDevcId(), k -> new ArrayList<>());
                tmp.add(task);
            }
        }

        return result;
    }

    /**
     * 初始化模具map
     *
     * @return
     */
    private Map<Integer, MldDtl> initMldDtlMap() {
        Map<Integer, MldDtl> map = new ConcurrentHashMap<>();
        List<MldDtl> mldDtls = mldDtlDao.findAll();
        if (mldDtls != null && mldDtls.size() > 0) {
            for (MldDtl mldDtl : mldDtls) {
                map.put(mldDtl.getMldDtlId(), mldDtl);
            }
        } else {
            log.warn("initMldDtlMap 警告 --> 没有模具信息");
        }

        return map;
    }

    /**
     * 初始化工单map
     *
     * @return
     */
    private Map<Integer, Task> initTaskMap() {
        Map<Integer, Task> map = new ConcurrentHashMap<>();

        List<Task> tasks = taskDao.findByStatusIsST00();
        if (tasks != null && tasks.size() > 0) {
            for (Task task : tasks) {
                map.put(task.getTaskId(), task);
            }
        } else {
            log.warn("警告 --> 符合条件的工单为空");
        }

        return map;
    }
}
