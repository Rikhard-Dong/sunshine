package com.hfmes.sunshine.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/13 19:09
 */
@Slf4j
public class DateUtilsTest {


    @Test
    public void calculateMinuteDifference() {
        Date curDate = new Date();
        Date beforeDate = new Date();

        beforeDate.setTime((curDate.getTime() - 1000 * 60 * 12));

        Long diff = DateUtils.calculateMinuteDifference(beforeDate, curDate);
        log.debug("diff --> {}", diff);
    }
}