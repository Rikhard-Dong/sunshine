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
}
