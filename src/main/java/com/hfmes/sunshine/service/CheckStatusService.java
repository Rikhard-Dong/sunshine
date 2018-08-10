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
     * @return 一直返回true, 否则返回false
     */
    Boolean checkDevcSync(Integer deviceId, String devcStatus);
}
