package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.Task;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
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

    Integer insertOne(Task newTask);


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

    /**
     * 更新生产开始时间
     *
     * @param taskId
     * @param startTime
     * @return
     */
    Integer updateStartTime(@Param("taskId") Integer taskId,
                            @Param("startTime") Date startTime);

    /**
     * 更新生产结束时间
     *
     * @param taskId
     * @param endTime
     * @return
     */
    Integer updateEndTime(@Param("taskId") Integer taskId,
                          @Param("endTime") Date endTime);

    /**
     * 更新模具安装开始时间
     *
     * @param taskId
     * @param mldStateDate
     * @return
     */
    Integer updateMLdStartTime(@Param("taskId") Integer taskId,
                               @Param("mldStartTime") Date mldStateDate);

    /**
     * 更新模具安装结束时间
     *
     * @param taskId
     * @param mldEndTime
     * @return
     */
    Integer updateMLdEndTime(@Param("taskId") Integer taskId,
                             @Param("mldEndTime") Date mldEndTime);


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

    /**
     * 根据planDtlId统计该planDtl总共生产数量
     *
     * @param planDtlId
     * @return
     */
    Integer sumProcNumByPlanDtlId(Integer planDtlId);

}
