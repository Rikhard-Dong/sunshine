package com.hfmes.sunshine.ws.impl;

import com.hfmes.sunshine.domain.Devc;
import com.hfmes.sunshine.domain.MldDtl;
import com.hfmes.sunshine.domain.SCMethod;
import com.hfmes.sunshine.domain.Task;
import com.hfmes.sunshine.dto.ConditionDto;
import com.hfmes.sunshine.dto.OptionDTO;
import com.hfmes.sunshine.dto.ParamsObj;
import com.hfmes.sunshine.dto.Result;
import com.hfmes.sunshine.service.*;
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
    private final CheckStatusService checkStatusService;
    private final SyncStatusService syncStatusService;
    private final CountNumService countNumService;
    private final StatusChangeService statusChangeService;
    private final TaskService taskService;

    @Autowired
    public OptionWebServiceImpl(OptionService optionService,
                                ConditionService conditionService,
                                CheckStatusService checkStatusService,
                                SyncStatusService syncStatusService,
                                CountNumService countNumService,
                                StatusChangeService statusChangeService,
                                TaskService taskService) {
        this.optionService = optionService;
        this.conditionService = conditionService;
        this.checkStatusService = checkStatusService;
        this.syncStatusService = syncStatusService;
        this.countNumService = countNumService;
        this.statusChangeService = statusChangeService;
        this.taskService = taskService;
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
     * @return 执行结果
     */
    @Override
    public String hasMould(String objStr) {
        ParamsObj params = getParamObj(objStr);
        Boolean result = conditionService.hasMould(params.getDeviceId());
        return StringUtils.capitalize(result.toString());
    }

    /**
     * @param objStr 参数对象json数据
     * @return 执行结果
     */
    @Override
    public String mldOpLegal(String objStr) {
        ParamsObj params = getParamObj(objStr);
        Boolean result = conditionService.mldOpLegal(params.getDeviceId(), params.getPersonId());
        return StringUtils.capitalize(result.toString());
    }

    /**
     * @param objStr 参数对象json数据
     * @return 执行结果
     */
    @Override
    public String isDeviceRun(String objStr) {
        ParamsObj params = getParamObj(objStr);
        Boolean result = conditionService.isDevcRun(params.getDeviceId());
        return StringUtils.capitalize(result.toString());
    }

    /**
     * @param objStr 参数对象json数据
     * @return 执行结果
     */
    @Override
    public String isMouldUse(String objStr) {
        ParamsObj params = getParamObj(objStr);
        Boolean result = conditionService.isMouldUse(params.getDeviceId());
        return StringUtils.capitalize(result.toString());
    }

    /**
     * @param objStr 参数对象json数据
     * @return 执行结果
     */
    @Override
    public String devOpLegal(String objStr) {
        ParamsObj params = getParamObj(objStr);
        Boolean result = conditionService.devOpLegal(params.getDeviceId(), params.getPersonId());
        return StringUtils.capitalize(result.toString());
    }

    /**
     * @param objStr 参数对象json数据
     * @return 执行结果
     */
    @Override
    public String isTaskDevOpEqualsCurPerson(String objStr) {
        ParamsObj params = getParamObj(objStr);
        Boolean result = conditionService.isTaskDevOpEqualsCurPerson(params.getDeviceId(), params.getPersonId());
        return StringUtils.capitalize(result.toString());
    }

    /**
     * @param objStr 参数对象json数据
     * @return 执行结果
     */
    @Override
    public String procNumAchieveSetNum(String objStr) {
        ParamsObj params = getParamObj(objStr);
        Boolean result = conditionService.procNumAchieveSetNum(params.getDeviceId());
        return StringUtils.capitalize(result.toString());
    }

    /**
     * @param objStr 参数对象json数据
     * @return 执行结果
     */
    @Override
    public String procNumLessThanSetNum(String objStr) {
        ParamsObj params = getParamObj(objStr);
        Boolean result = conditionService.procNumAchieveSetNum(params.getDeviceId());
        return StringUtils.capitalize(result.toString());
    }



    /* *****************************************************
     * 生产计数接口
     *****************************************************/

    @Override
    public String updateLocalToServerCount(String devcId, String count) {
        Boolean result = countNumService.updateLocalToServerCount(devcId, count);
        return StringUtils.capitalize(result.toString());
    }


    /* *****************************************************
     * 检查同步情况接口
     *****************************************************/

    /**
     * @param devcId     上传设备id
     * @param devcStatus 上传设备状态
     * @return 一致返回true, 不一致返回false
     */
    @Override
    public String checkDSSame(String devcId, String devcStatus) {
        Boolean result = checkStatusService.checkDevcStatus(Integer.valueOf(devcId), devcStatus);
        return JacksonUtils.toJSon(Result.success(StringUtils.capitalize(result.toString())));
    }

    /**
     * @param mldId     上传模具id
     * @param mldStatus 上传模具状态
     * @return 一致返回true, 不一致返回false
     */
    @Override
    public String checkMSSame(String mldId, String mldStatus) {
        Boolean result = checkStatusService.checkMldStatus(Integer.valueOf(mldId), mldStatus);

        return JacksonUtils.toJSon(Result.success(StringUtils.capitalize(result.toString())));
    }

    /**
     * @param taskId     工单id
     * @param taskStatus 工单状态
     * @return 一致返回true, 不一致返回false
     */
    @Override
    public String checkTSSame(String taskId, String taskStatus) {
        Boolean result = checkStatusService.checkTaskMldStatus(Integer.valueOf(taskId), taskStatus);
        return JacksonUtils.toJSon(Result.success(StringUtils.capitalize(result.toString())));
    }


    /* *****************************************************
     * 实现状态同步接口
     *****************************************************/

    @Override
    public String updateDSServToLocal(String devcId) {
        Devc devc = syncStatusService.syncDevc(Integer.valueOf(devcId));

        Result result = Result.success(devc);
        return JacksonUtils.toJSon(devc);
    }

    @Override
    public String updateMSServToLocal(String mldId) {
        MldDtl mldDtl = syncStatusService.syncMldDtl(Integer.valueOf(mldId));

        Result result = Result.success(mldDtl);
        return JacksonUtils.toJSon(mldDtl);
    }

    @Override
    public String updateTSServToLocal(String taskId) {
        Task task = syncStatusService.syncTask(Integer.valueOf(taskId));

        Result result = Result.success(task);
        return JacksonUtils.toJSon(task);
    }

    /* *****************************************************
     * 工单下发操作
     *****************************************************/

    /**
     * @param devcId 设备ID
     * @return result
     */
    @Override
    public String taskDown(String devcId) {
        taskService.taskDown(Integer.valueOf(devcId));
        return JacksonUtils.toJSon(Result.success());
    }

    /* *****************************************************
     * 实现管理端强制转换状态机状态
     *****************************************************/

    /**
     * @param deviceId 设备id
     * @param status   强制更新设备状态
     * @return result
     */
    @Override
    public String changeDeviceStateMachineStatus(String deviceId, String status) {
        statusChangeService.changeDeviceStateMachineStatus(Integer.valueOf(deviceId), status);

        return JacksonUtils.toJSon(Result.success());
    }

    /**
     * @param mouldId 模具id
     * @param status  模具状态
     * @return result
     */
    @Override
    public String changeMouldStateMachineStatus(String mouldId, String status) {
        statusChangeService.changeMouldStateMachineStatus(Integer.valueOf(mouldId), status);
        return JacksonUtils.toJSon(Result.success());
    }

    /* *****************************************************
     * 执行动作
     *****************************************************/

    /**
     * @param opIdStr     操作员id
     * @param optionIdStr 操作id
     * @param deviceIdStr 设备id
     * @return
     */
    @Override
    public String btnPressOpAction(String opIdStr, String optionIdStr, String deviceIdStr, String mldIdStr) {
        Integer opId = StringUtils.isNumeric(opIdStr) ? Integer.valueOf(opIdStr) : null;
        Integer optionId = StringUtils.isNumeric(optionIdStr) ? Integer.valueOf(optionIdStr) : null;
        Integer deviceId = StringUtils.isNumeric(deviceIdStr) ? Integer.valueOf(deviceIdStr) : null;
        Integer mldId = StringUtils.isNumeric(mldIdStr) ? Integer.valueOf(mldIdStr) : null;


        optionService.exceOption(opId, optionId, deviceId, mldId);

        return JacksonUtils.toJSon(Result.success());
    }



    /* *****************************************************
     * 内部方法
     *****************************************************/

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
