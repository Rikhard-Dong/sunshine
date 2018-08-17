package com.hfmes.sunshine.service;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/10 15:58
 */
public interface CheckStatusService {

    /**
     * 判断设备同步状态
     *
     * @param deviceId   设备id
     * @param devcStatus 下位机上传状态
     * @return 一致返回true, 否则返回false
     */
    Boolean checkDevcStatus(Integer deviceId, String devcStatus, Integer taskId);


    /**
     * 检查模具同步状态
     *
     * @param mldId     模具id
     * @param mldStatus 模具状态
     * @return 一致返回true, 否则返回false
     */
    Boolean checkMldStatus(Integer mldId, String mldStatus);

    /**
     * 检查工单同步状态
     *
     * @param taskId     工单id
     * @param taskStatus 工单状态
     * @return 一致返回true, 否则返回false
     */
    Boolean checkTaskMldStatus(Integer taskId, String taskStatus);
}
