package com.hfmes.sunshine.ws;

import org.apache.ibatis.annotations.Param;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import java.io.UnsupportedEncodingException;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 19:13
 */
@BindingType(value = SOAPBinding.SOAP12HTTP_BINDING)
@WebService(name = "testWebService", targetNamespace = "http://ws.sunshine.hfmes.com")
public interface TestWebService {
    @WebMethod(operationName = "test", action = "测试")
    public String test();

    @WebMethod
    public String test1(@WebParam(name = "obj") String obj);
}
