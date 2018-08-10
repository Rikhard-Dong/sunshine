package com.hfmes.sunshine.service;


/**
 * @author supreDong@gmail.com
 * @date 2018/8/10 9:54
 * <p>
 * 返回条件判断结果
 */
public interface ConditionService {
    /**
     * 1. 判断设备是否拥有模具
     *
     * @param deviceId 设备Id
     * @return boolean true 有, false 没有
     */
    Boolean hasMould(Integer deviceId);

    /**
     * 2. 装模人员未设定或刷卡人员
     *
     * @param deviceId 设备Id
     * @return 未设定返回true, 设定返回false
     */
    Boolean mldNotSet(Integer deviceId);

    /**
     * 3. 判断设备是否运行
     *
     * @param deviceId 设备id
     * @return 运行 true 不运行 false
     */
    Boolean isDevcRun(Integer deviceId);

    /**
     * 4. 模具状态为使用
     *
     * @param deviceId 设备id
     * @return 使用 True
     */
    Boolean isMouldUse(Integer deviceId);

    /**
     * 6. 当前工单的操作者为刷卡者
     *
     * @param deviceId 设备id
     * @param personId 刷卡者id
     * @return 一致返回True 否则 False
     */
    Boolean isTaskDevOpEqualsCurPerson(Integer deviceId, Integer personId);

    /**
     * 7. 生产数量达到设定数量
     *
     * @param deviceId 设备Id
     * @return 达到返回True
     */
    Boolean procNumAchieveSetNum(Integer deviceId);

    /**
     * 8. 生产数量小于设定数量
     *
     * @param devcId 设备Id
     * @return 小于返回True
     */
    Boolean procNumLessThanSetNum(Integer devcId);

}
