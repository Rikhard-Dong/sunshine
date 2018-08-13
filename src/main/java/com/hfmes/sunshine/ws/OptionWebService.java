package com.hfmes.sunshine.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 14:35
 */
@BindingType(value = SOAPBinding.SOAP12HTTP_BINDING)
@WebService(name = "optionWebService", targetNamespace = "http://ws.sunshine.hfmes.com")
public interface OptionWebService {

    /**
     * 获取当前所有操作
     *
     * @param cardNo   刷卡人员卡号
     * @param deviceId 设备id
     * @return
     */
    @WebMethod
    String obtainOp(@WebParam(name = "personCardNo") String cardNo,
                    @WebParam(name = "deviceId") String deviceId);


    /**
     * 获取该操作所需的条件
     *
     * @param opId 操作id
     * @return 包含操作条件list的json数据
     */
    @WebMethod
    String obtainConditions(@WebParam(name = "opId") String opId);

    /**
     * 获取所有动作
     *
     * @param opId 操作id
     * @return 包含动作list的json数据
     */
    @WebMethod
    String obtainMethods(@WebParam(name = "opId") String opId);

    /* *****************************************************
     * 条件判断
     *****************************************************/

    /**
     * 1. 当前设备有无模具
     *
     * @param obj 参数对象json数据
     * @return true 有 false 无
     */
    @WebMethod
    String hasMould(@WebParam(name = "obj") String obj);

    /**
     * 2. 装模人员未设定或刷卡人员
     *
     * @param objStr 参数对象json数据
     * @return 符合条件返回true
     */
    @WebMethod
    String mldOpLegal(@WebParam(name = "obj") String objStr);

    /**
     * 3. 设备是否运行
     *
     * @param obj 参数对象json数据
     * @return True 运行
     */
    @WebMethod
    String isDeviceRun(@WebParam(name = "obj") String obj);

    /**
     * 4. 模具状态为使用
     *
     * @param obj 参数对象json数据
     * @return True 使用
     */
    @WebMethod
    String isMouldUse(@WebParam(name = "obj") String obj);

    /**
     * 5. 当前工单为操作工或者未指定
     *
     * @param objStr 参数对象json数据
     * @return
     */
    @WebMethod
    String devOpLegal(@WebParam(name = "obj") String objStr);

    /**
     * 6. 当前工单的操作者为刷卡者
     *
     * @param objStr 参数对象json数据
     * @return 一致True
     */
    @WebMethod
    String isTaskDevOpEqualsCurPerson(@WebParam(name = "obj") String objStr);

    /**
     * 7. 生产数量达到设定数量
     *
     * @param objStr 参数对象json数据
     * @return 达到返回true
     */
    @WebMethod
    String procNumAchieveSetNum(@WebParam(name = "obj") String objStr);

    /**
     * 8. 生产数量小于设定数量
     *
     * @param objStr 参数对象json数据
     * @return 小于返回true
     */
    @WebMethod
    String procNumLessThanSetNum(@WebParam(name = "obj") String objStr);

    /* *****************************************************
     * 生产计数接口
     *****************************************************/


    /**
     * 更新服务器对象计数器的值
     *
     * @param devcId
     * @param count
     * @return
     */
    @WebMethod
    String updateLocalToServerCount(@WebParam(name = "devcId") String devcId,
                                    @WebParam(name = "count") String count);


    /* *****************************************************
     * 检查同步情况接口
     *****************************************************/

    /**
     * 检查设备状态是否同步
     *
     * @param devcId     上传设备id
     * @param devcStatus 上传设备状态
     * @return 一致返回true, 不一致返回false
     */
    @WebMethod
    String checkDSSame(@WebParam(name = "devcId") String devcId,
                       @WebParam(name = "devcStatus") String devcStatus);


    /**
     * 检查模具状态是否同步
     *
     * @param mldId     上传模具id
     * @param mldStatus 上传模具状态
     * @return 一致返回true, 不一致返回false
     */
    @WebMethod
    String checkMSSame(@WebParam(name = "mldId") String mldId,
                       @WebParam(name = "mldStatus") String mldStatus);

    /**
     * 检查工单状态是否同步
     *
     * @param taskId     工单id
     * @param taskStatus 工单状态
     * @return 一致返回true, 不一致返回false
     */
    @WebMethod
    String checkTSSame(@WebParam(name = "taskId") String taskId,
                       @WebParam(name = "taskStatus") String taskStatus);

    /* *****************************************************
     * 实现状态同步接口
     *****************************************************/


    /**
     * 同步设备信息
     *
     * @param devcId device id
     * @return
     */
    @WebMethod
    String updateDSServToLocal(@WebParam(name = "devcId") String devcId);

    /**
     * 同步模具信息
     *
     * @param mldId 模具id
     * @return
     */
    @WebMethod
    String updateMSServToLocal(@WebParam(name = "mldId") String mldId);

    /**
     * 同步工单信息
     *
     * @param taskId 工单id
     * @return
     */
    @WebMethod
    String updateTSServToLocal(@WebParam(name = "taskId") String taskId);

    /* *****************************************************
     * 工单下发
     *****************************************************/


    /**
     * 工单下操作
     *
     * @param devcId 设备ID
     * @return
     */
    @WebMethod
    String taskDown(@WebParam(name = "devcId") String devcId);

    /* *****************************************************
     * 实现管理端强制转换状态机状态
     *****************************************************/

    /**
     * 强制更新状态机状态
     *
     * @param deviceId 设备id
     * @param status   强制更新设备状态
     * @return
     */
    @WebMethod
    String changeDeviceStateMachineStatus(@WebParam(name = "deviceId") String deviceId,
                                          @WebParam(name = "status") String status);

    /**
     * 强制更新模具状态
     *
     * @param mouldId 模具id
     * @param status  模具状态
     * @return
     */
    @WebMethod
    String changeMouldStateMachineStatus(@WebParam(name = "mouldId") String mouldId,
                                         @WebParam(name = "status") String status);

    /* *****************************************************
     * 执行动作
     *****************************************************/

    /**
     * 执行动作
     *
     * @param opId     操作员id
     * @param optionId 操作ID
     * @param deviceId 设备id
     * @param mldId    模具id
     * @return
     */
    @WebMethod
    String btnPressOpAction(@WebParam(name = "opId") String opId,
                            @WebParam(name = "optionId") String optionId,
                            @WebParam(name = "deviceId") String deviceId,
                            @WebParam(name = "mldId") String mldId);

}
