package com.hfmes.sunshine.ws.impl;

import com.hfmes.sunshine.dto.ParamsObj;
import com.hfmes.sunshine.dto.Result;
import com.hfmes.sunshine.utils.JacksonUtils;
import com.hfmes.sunshine.ws.TestWebService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.jws.WebService;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 19:14
 */
@Slf4j
@Component
@WebService(name = "testWebService", targetNamespace = "http://ws.sunshine.hfmes.com",
        endpointInterface = "com.hfmes.sunshine.ws.TestWebService")
public class TestWebServiceImpl implements TestWebService {
    @Override
    public String test() {
        return JacksonUtils.toJSon(Result.success("返回数据"));
    }

    @Override
    public String test1(String obj) {

        log.debug("obj --> {}", obj);

//        String objStr = new String(obj, "ISO-8859-1");
//        log.debug("objStr --> {}", objStr);
        ParamsObj paramsObj = JacksonUtils.readValue(obj, ParamsObj.class);

        log.debug("paramsObj --> {}", paramsObj);

        return Boolean.FALSE.toString();
    }
}
