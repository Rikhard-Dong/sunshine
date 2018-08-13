package com.hfmes.sunshine.ws;

import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/12 14:10
 */
@Slf4j
public class OptionWebServiceTest {
    private Client client;

    @Before
    public void setUp() throws Exception {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        client = dcf.createClient("http://localhost:8080/services/options?wsdl");
    }

    @Test
    public void updateDSServToLocalTest() {
        Object[] objs;

        try {
            objs = client.invoke("updateDSServToLocal", "2");

            log.debug("result --> {}", objs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateTSServToLocal() {
        Object[] objs;

        try {
            objs = client.invoke("updateTSServToLocal", "128");

            log.debug("result --> {}", objs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}