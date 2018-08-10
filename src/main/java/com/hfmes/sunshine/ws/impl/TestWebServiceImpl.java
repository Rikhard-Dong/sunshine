package com.hfmes.sunshine.ws.impl;

import com.hfmes.sunshine.dto.Result;
import com.hfmes.sunshine.utils.JacksonUtils;
import com.hfmes.sunshine.ws.TestWebService;
import org.springframework.stereotype.Component;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 19:14
 */
@Component
@WebService(name = "testWebService", targetNamespace = "http://ws.sunshine.hfmes.com",
        endpointInterface = "com.hfmes.sunshine.ws.TestWebService")
public class TestWebServiceImpl implements TestWebService {
    @Override
    public String test() {
        return JacksonUtils.toJSon(Result.success("返回数据"));
    }
}
