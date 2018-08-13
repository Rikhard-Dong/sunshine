package com.hfmes.sunshine.utils;

import java.util.Date;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/13 19:06
 */
public class DateUtils {

    /**
     * 计算两个时间之间相差多少分钟
     *
     * @param date1 较前的一个时间
     * @param date2 较后的一个时间
     * @return 相差分钟
     */
    public static Long calculateMinuteDifference(Date date1, Date date2) {
        Long timestamp1 = date1.getTime();
        Long timeStamp2 = date2.getTime();

        Long diff = timeStamp2 - timestamp1;

        return diff / (1000 * 60);
    }
}
