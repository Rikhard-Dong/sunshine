package com.hfmes.sunshine.config;

import com.hfmes.sunshine.ws.OptionWebService;
import com.hfmes.sunshine.ws.TestWebService;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 14:27
 * cxf webservice 配置
 */
@Configuration
public class CXFConfig {

    private final Bus bus;

    private final OptionWebService optionWebService;

    private final TestWebService testWebService;

    @Autowired
    public CXFConfig(Bus bus, OptionWebService optionWebService, TestWebService testWebService) {
        this.bus = bus;
        this.optionWebService = optionWebService;
        this.testWebService = testWebService;
    }

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, optionWebService);
        endpoint.publish("/options");
        return endpoint;
    }

    @Bean
    public EndpointImpl testEndPoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, testWebService);
        endpoint.publish("/test");
        return endpoint;
    }
}
