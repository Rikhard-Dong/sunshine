package com.hfmes.sunshine.ws.impl;

import com.hfmes.sunshine.domain.SCMethod;
import com.hfmes.sunshine.dto.ConditionDto;
import com.hfmes.sunshine.dto.OptionDTO;
import com.hfmes.sunshine.dto.ParamsObj;
import com.hfmes.sunshine.dto.Result;
import com.hfmes.sunshine.service.ConditionService;
import com.hfmes.sunshine.service.OptionService;
import com.hfmes.sunshine.utils.JacksonUtils;
import com.hfmes.sunshine.ws.OptionWebService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
    private final ConditionService conditionService;

    @Autowired
    public OptionWebServiceImpl(OptionService optionService,
                                ConditionService conditionService) {
        this.optionService = optionService;
        this.conditionService = conditionService;
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
        List<OptionDTO> result = optionService.obtainOptions(cardNo, devcId);
        return JacksonUtils.toJSon(Result.success(result));
    }


    /**
     * @param opId 操作id
     * @return 包含操作条件list的json数据
     */
    @Override
    public String obtainConditions(String opId) {
        log.debug("opId --> {}", opId);

        List<ConditionDto> result = optionService.obtainConditions(Integer.valueOf(opId));
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


    /* *****************************************************
     * 条件判断
     *****************************************************/

    /**
     * @param objStr 参数对象json数据
     * @return
     */
    @Override
    public String hasMould(String objStr) {
        ParamsObj params = getParamObj(objStr);
        Boolean result = conditionService.hasMould(params.getDeviceId());
        return StringUtils.capitalize(result.toString());
    }

    @Override
    public String mldOpLegal(String objStr) {
        ParamsObj params = getParamObj(objStr);
        Boolean result = conditionService.mldOpLegal(params.getDeviceId(), params.getPersonId());
        return StringUtils.capitalize(result.toString());
    }

    /**
     * @param objStr 参数对象json数据
     * @return
     */
    @Override
    public String isDeviceRun(String objStr) {
        ParamsObj params = getParamObj(objStr);
        Boolean result = conditionService.isDevcRun(params.getDeviceId());
        return StringUtils.capitalize(result.toString());
    }

    /**
     * @param objStr 参数对象json数据
     * @return
     */
    @Override
    public String isMouldUse(String objStr) {
        ParamsObj params = getParamObj(objStr);
        Boolean result = conditionService.isMouldUse(params.getDeviceId());
        return StringUtils.capitalize(result.toString());
    }

    @Override
    public String devOpLegal(String objStr) {
        ParamsObj params = getParamObj(objStr);
        Boolean result = conditionService.devOpLegal(params.getDeviceId(), params.getPersonId());
        return StringUtils.capitalize(result.toString());
    }

    /**
     * @param objStr 参数对象json数据
     * @return
     */
    @Override
    public String isTaskDevOpEqualsCurPerson(String objStr) {
        ParamsObj params = getParamObj(objStr);
        Boolean result = conditionService.isTaskDevOpEqualsCurPerson(params.getDeviceId(), params.getPersonId());
        return StringUtils.capitalize(result.toString());
    }

    /**
     * @param objStr 参数对象json数据
     * @return
     */
    @Override
    public String procNumAchieveSetNum(String objStr) {
        ParamsObj params = getParamObj(objStr);
        Boolean result = conditionService.procNumAchieveSetNum(params.getDeviceId());
        return StringUtils.capitalize(result.toString());
    }

    /**
     * @param objStr 参数对象json数据
     * @return
     */
    @Override
    public String procNumLessThanSetNum(String objStr) {
        ParamsObj params = getParamObj(objStr);
        Boolean result = conditionService.procNumAchieveSetNum(params.getDeviceId());
        return StringUtils.capitalize(result.toString());
    }

    /**
     * 将json数据转换成对象
     *
     * @param objStr json数据
     * @return param参数对象
     */
    private ParamsObj getParamObj(String objStr) {
        log.debug("objStr json ---> {}", objStr);
        ParamsObj params = JacksonUtils.readValue(objStr, ParamsObj.class);
        log.debug("param --> {}", params);
        return params;
    }
}
