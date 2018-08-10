package com.hfmes.sunshine.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 19:13
 */
@BindingType(value = SOAPBinding.SOAP12HTTP_BINDING)
@WebService(name = "testWebService", targetNamespace = "http://ws.sunshine.hfmes.com")
public interface TestWebService {
    @WebMethod
    public String test();
}
