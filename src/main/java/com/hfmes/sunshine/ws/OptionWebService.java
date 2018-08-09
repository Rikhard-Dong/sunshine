package com.hfmes.sunshine.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 14:35
 */
@WebService
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
                    @WebParam(name = "deviceId") Integer deviceId);
}
