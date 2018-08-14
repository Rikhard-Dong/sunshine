package com.hfmes.sunshine.service;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/13 13:51
 * <p>
 * 操作执行服务, 对应SCOption中的数据
 */
public interface OptionExceService {

    /**
     * opId --> 1 装模/料
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    void mouldFilling(Integer opId,
                      Integer optionId,
                      Integer devcId,
                      Integer mldDtlId);

    /**
     * opId --> 2 装模完成
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    void completeMouldFilling(Integer opId,
                              Integer optionId,
                              Integer devcId,
                              Integer mldDtlId);

    /**
     * opId --> 3 卸模/料
     * 模具状态从使用状态到卸模
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    void demoulding(Integer opId,
                    Integer optionId,
                    Integer devcId,
                    Integer mldDtlId);


    /**
     * opId --> 4  卸模完成
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    void completeDemoulding(Integer opId,
                            Integer optionId,
                            Integer devcId,
                            Integer mldDtlId);


    /**
     * opId --> 5 模具维修
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    void mouldRepair(Integer opId,
                     Integer optionId,
                     Integer devcId,
                     Integer mldDtlId);

    /**
     * opId --> 6  卸模/料
     * 模具状态从模具检修到模具检修
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    void demoulding2(Integer opId,
                     Integer optionId,
                     Integer devcId,
                     Integer mldDtlId);

    /**
     * opId --> 7 模具修复
     * 从模具检修到使用
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    void mouldRepairComplete(Integer opId,
                             Integer optionId,
                             Integer devcId,
                             Integer mldDtlId);

    /**
     * opId --> 8 模具修复
     * 从模具检修到领用
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    void mouldRepairComplete2(Integer opId,
                              Integer optionId,
                              Integer devcId,
                              Integer mldDtlId);

    /**
     * opId --> 9 开始生产
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    void startProduce(Integer opId,
                      Integer optionId,
                      Integer devcId,
                      Integer mldDtlId);

    /**
     * opId --> 10 继续生产
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    void continueProduce(Integer opId,
                         Integer optionId,
                         Integer devcId,
                         Integer mldDtlId);

    /**
     * opId --> 11 生产完成
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    void completeProduce(Integer opId,
                         Integer optionId,
                         Integer devcId,
                         Integer mldDtlId);

    /**
     * opId --> 12 暂停生产
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    void suspendProduce(Integer opId,
                        Integer optionId,
                        Integer devcId,
                        Integer mldDtlId);

    /**
     * opId --> 13 设备故障
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    void deviceFault(Integer opId,
                     Integer optionId,
                     Integer devcId,
                     Integer mldDtlId);

    /**
     * opId --> 14 模具故障
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    void mouldFault(Integer opId,
                    Integer optionId,
                    Integer devcId,
                    Integer mldDtlId);

    /**
     * opId --> 15 设备撤销报修
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    void revokeDeviceReportRepair(Integer opId,
                                  Integer optionId,
                                  Integer devcId,
                                  Integer mldDtlId);

    /**
     * opId --> 16 模具撤销报修
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    void revokeDeviceReportRepair2(Integer opId,
                                   Integer optionId,
                                   Integer devcId,
                                   Integer mldDtlId);

    /**
     * opId --> 17 设备维修
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    void repairDevice(Integer opId,
                      Integer optionId,
                      Integer devcId,
                      Integer mldDtlId);

    /**
     * opId --> 18 设备修复
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    void completeRepairDevice(Integer opId,
                              Integer optionId,
                              Integer devcId,
                              Integer mldDtlId);

    /**
     * opId --> 19 生产验收
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    void checkProduce(Integer opId,
                      Integer optionId,
                      Integer devcId,
                      Integer mldDtlId);

    /**
     * opId --> 20 继续生产
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    void continueProduce2(Integer opId,
                          Integer optionId,
                          Integer devcId,
                          Integer mldDtlId);

    /**
     * opID --> 21 中止生产
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param devcId   设备id
     * @param mldDtlId 模具id
     */
    void stopProduce(Integer opId,
                     Integer optionId,
                     Integer devcId,
                     Integer mldDtlId);
}
