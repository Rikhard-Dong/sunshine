package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.Task;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 22:08
 * <p>
 * 任务
 */
public interface TaskDao {

    /* ****************************************
     * update
     ******************************************/

    /**
     * 更新工单状态
     *
     * @param taskId 工单id
     * @param status 更新状态
     * @return 更新行数
     */
    Integer updateStatus(@Param("taskId") Integer taskId,
                         @Param("status") String status);

    Integer updateProcNum(@Param("taskId") Integer taskId,
                          @Param("procNum") Integer procNum);

    Integer updateTestNum(@Param("taskId") Integer taskId,
                          @Param("testNum") Integer testNum);


    /* ****************************************
     * Select
     ******************************************/

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
     * 查询所有状态为ST00分配的工单和设备中的模具
     *
     * @return list of task while task status is ST00
     */
    List<Task> findByStatusIsST00AndDevTask();

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

    List<Task> findByStatusIsST00ByDevcId(Integer deviceId);
}
