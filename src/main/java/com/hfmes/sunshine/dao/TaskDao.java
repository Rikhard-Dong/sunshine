package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.Task;

import java.util.List;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 22:08
 * <p>
 * 任务
 */
public interface TaskDao {
    /**
     * 查找所有的任务
     *
     * @return list of all task
     */
    List<Task> findAll();

    /**
     * 查找所有状态不为ST40(完工)的任务
     *
     * @return list of status
     */
    List<Task> findAllStatusNotEqualST40();

    /**
     * 查询所有状态为ST00分配的工单
     *
     * @return list of task while task status is ST00
     */
    List<Task> findByStatusIsST00();

    /**
     * 根据任务ID查找
     *
     * @param taskId task id
     * @return task
     */
    Task findByTaskId(Integer taskId);

    /**
     * 根据工单id获取当前工单的状态
     *
     * @param taskId 工单id
     * @return 当前工单的状态
     */
    String getStatusByTaskId(Integer taskId);

}
