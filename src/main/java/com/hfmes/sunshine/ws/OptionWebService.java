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


}
