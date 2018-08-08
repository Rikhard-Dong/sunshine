package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.Task;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 22:08
 */
public interface TaskDao {
    Task findByTaskId(Integer taskid);
}
