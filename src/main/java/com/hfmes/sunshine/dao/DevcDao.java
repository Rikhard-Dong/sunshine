package com.hfmes.sunshine.dao;

import com.hfmes.sunshine.domain.Devc;
import com.hfmes.sunshine.enums.DeviceStatus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 20:34
 */
public interface DevcDao {

    /* ****************************************
     * update
     ******************************************/

    /**
     * 更新设备的工单id
     *
     * @param deviceId 设备id
     * @param taskId   工单id
     * @return 受影响行数
     */
    Integer updateTaskId(@Param("devcId") Integer deviceId,
                         @Param("taskId") Integer taskId);

    /**
     * 更新设备的模具信息及模具状态
     *
     * @param devcId    设备id
     * @param mldDtlId  模具id
     * @param mldStatus 模具状态
     * @return 受影响行数
     */
    Integer updateMldDtlIdAndMldStatus(@Param("devcId") Integer devcId,
                                       @Param("mldDtlId") Integer mldDtlId,
                                       @Param("mldDtlStatus") String mldStatus);

    /**
     * 更新设备状态
     *
     * @param deviceId
     * @param status
     * @return
     */
    Integer updateStatus(@Param("devcId") Integer deviceId,
                         @Param("status") String status);


    /* ****************************************
     * Select
     ******************************************/

    /**
     * 根据设备id查找
     *
     * @param id 设备id
     * @return devc
     */
    Devc findByDeviceId(Integer id);

    /**
     * 查找所有设备
     *
     * @return list of devc
     */
    List<Devc> findAll();

    /**
     * 获取设备的状态
     *
     * @param deviceId 设备id
     * @return 设备状态
     */
    DeviceStatus getStatusByDeviceId(Integer deviceId);


}
