package com.hfmes.sunshine.config;

import com.hfmes.sunshine.cache.*;
import com.hfmes.sunshine.dao.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/16 16:21
 * <p>
 * 初始化缓存数据
 */
@Component
@Slf4j
public class CacheInitializer implements CommandLineRunner {

    @Autowired
    private DevcDao devcDao;

    @Autowired
    private PersonDao personDao;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private MldDtlDao mldDtlDao;

    @Autowired
    private SCOptionDao optionDao;


    @Override
    public void run(String... args) throws Exception {
        log.info("初始化缓存信息........");

        DevcCache.init(devcDao);
        PersonCache.init(personDao);
        Person2Cache.init(personDao);
        CountNumsCache.init(devcDao);
        DevcTasksCache.init(taskDao);
        MldDtlsCache.init(mldDtlDao);
        TasksCache.init(taskDao);
        OptionsCache.init(optionDao);
    }
}
