package com.hfmes.sunshine.ws.impl;

import com.hfmes.sunshine.domain.SCCondition;
import com.hfmes.sunshine.domain.SCMethod;
import com.hfmes.sunshine.dto.OptionsDTO;
import com.hfmes.sunshine.dto.Result;
import com.hfmes.sunshine.service.OptionService;
import com.hfmes.sunshine.utils.JacksonUtils;
import com.hfmes.sunshine.ws.OptionWebService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import java.util.List;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 14:37
 */
@Slf4j
@Component
@WebService(name = "optionWebService", targetNamespace = "http://ws.sunshine.hfmes.com",
        endpointInterface = "com.hfmes.sunshine.ws.OptionWebService")
public class OptionWebServiceImpl implements OptionWebService {

    private final OptionService optionService;

    @Autowired
    public OptionWebServiceImpl(OptionService optionService) {
        this.optionService = optionService;
    }

    /**
     * @param cardNo   刷卡人员卡号
     * @param deviceId 设备id
     * @return 包含可操作的信息
     */
    @Override
    public String obtainOp(String cardNo, String deviceId) {
        log.debug("cardNo --> {}, deviceId --> {}", cardNo, deviceId);

        Integer devcId = Integer.valueOf(deviceId);
        List<OptionsDTO> result = optionService.obtainOptions(cardNo, devcId);
        return JacksonUtils.toJSon(Result.success(result));
    }


    /**
     * @param opId 操作id
     * @return 包含操作条件list的json数据
     */
    @Override
    public String obtainConditions(String opId) {
        log.debug("opId --> {}", opId);

        List<SCCondition> result = optionService.obtainConditions(Integer.valueOf(opId));
        return JacksonUtils.toJSon(Result.success(result));
    }

    /**
     * @param opId 操作id
     * @return 包含执行动作list的json
     */
    @Override
    public String obtainMethods(String opId) {
        log.debug("opId --> {}", opId);
        List<SCMethod> result = optionService.obtainMethods(Integer.valueOf(opId));
        return JacksonUtils.toJSon(Result.success(result));
    }
}
