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
     * @return task中mldOpId为0或者和当前刷卡的相等则返回true
     */
    Boolean mldOpLegal(Integer deviceId, Integer personId);

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
     * 5. 当前工单为操作工或者未指定
     *
     * @param deviceId 设备id
     * @param personId 刷卡人id
     * @return task中devOpId为0或者和当前刷卡的相等则返回true
     */
    Boolean devOpLegal(Integer deviceId, Integer personId);

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

    /**
     * 9. 是否拥有模具和当前设备模具相同的下一单
     * <p>
     *
     * @param devcId 设备Id
     * @return 有下一单返回true
     */
    Boolean hasNextTask(Integer devcId);

    /**
     * 10 判断当前工单的模具是否与当前设备的模具相等
     *
     * @param devcId
     * @return
     */
    Boolean isMouldSame(Integer devcId);

}
