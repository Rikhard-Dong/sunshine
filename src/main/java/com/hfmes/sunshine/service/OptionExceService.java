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
}
