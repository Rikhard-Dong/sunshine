package com.hfmes.sunshine.service.impl;

import com.hfmes.sunshine.dao.*;
import com.hfmes.sunshine.domain.*;
import com.hfmes.sunshine.dto.ConditionDto;
import com.hfmes.sunshine.dto.OptionDTO;
import com.hfmes.sunshine.dto.Result;
import com.hfmes.sunshine.service.OptionExceService;
import com.hfmes.sunshine.service.OptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.hfmes.sunshine.utils.Constants.*;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 16:17
 */
@Service
@Slf4j
public class OptionServiceImpl implements OptionService {

    private final SCOptionDao optionDao;
    private final RoleDao roleDao;
    private final TaskDao taskDao;
    private final SCConditionDao conditionDao;
    private final SCMethodDao methodDao;
    private final SCOptCondDao scOptCondDao;
    private final Map<Integer, Devc> devcs;
    private final OptionExceService optionExceService;


    @Autowired
    public OptionServiceImpl(SCOptionDao optionDao,
                             RoleDao roleDao,
                             TaskDao taskDao,
                             SCConditionDao conditionDao,
                             SCMethodDao methodDao,
                             SCOptCondDao scOptCondDao,
                             @Qualifier("devcs") Map<Integer, Devc> devcs,
                             OptionExceService optionExceService) {
        this.optionDao = optionDao;
        this.roleDao = roleDao;
        this.taskDao = taskDao;
        this.conditionDao = conditionDao;
        this.methodDao = methodDao;
        this.scOptCondDao = scOptCondDao;
        this.devcs = devcs;
        this.optionExceService = optionExceService;
    }

    /**
     * 获取当刷卡用户可操作的菜单按钮
     *
     * @param cardNo   卡号
     * @param deviceId 设备id
     * @return list of optionDTO
     */
    @Override
    public List<OptionDTO> obtainOptions(String cardNo, Integer deviceId) {
        Devc devc = devcs.get(deviceId);

        if (devc == null) {
            // TODO 传入的设备不在map中
            log.warn("警告 --> 当前没有设备信息");
            return null;
        }

        String deviceStatus = devc.getStatus();
        // Task task = taskDao.findByTaskId(devc.getTaskId());
        Task task = devc.getTask();
        String taskStatus = task.getStatus();
        String mouldStatus = task.getMldDtl() != null ? task.getMldDtl().getStatus() : null;

        log.debug("deviceStatus --> {}, mouldStatus --> {}, taskStatus --> {}", deviceStatus, mouldStatus, taskStatus);

        // 获取操作的交集
        Set<SCOption> options = optionDao.findByCardNo(cardNo);
        log.debug("####options --> {}", options);
        options.retainAll(optionDao.findBySDStatus(deviceStatus));
        log.debug("####options --> {}", options);
        options.retainAll(optionDao.findBySMStatus(mouldStatus));
        log.debug("####options --> {}", options);
        options.retainAll(optionDao.findBySTStatus(taskStatus));
        log.debug("options --> {}", options);

        List<OptionDTO> optionDTOS = new ArrayList<>();

        for (SCOption option : options) {
            String type = getType(option.getRoleId());
            OptionDTO optionDTO = new OptionDTO(option, type);
            optionDTOS.add(optionDTO);

        }
        return optionDTOS;
    }

    /**
     * @param opId opID
     * @return
     */
    @Override
    public List<ConditionDto> obtainConditions(Integer opId) {
        List<SCCondition> conditions = conditionDao.findByOpId(opId);
        List<ConditionDto> conditionDtos = new ArrayList<>();

        for (SCCondition condition : conditions) {
            Boolean value = scOptCondDao.getValueByOptIdAndConditionId(opId, condition.getScConditionId());
            conditionDtos.add(new ConditionDto(condition, value));
        }

        return conditionDtos;
    }

    /**
     * @param opId opId
     * @return list of method
     */
    @Override
    public List<SCMethod> obtainMethods(Integer opId) {
        return methodDao.findByOpId(opId);
    }

    /**
     * 执行动作
     *
     * @param opId     操作员id
     * @param optionId 操作id
     * @param deviceId 设备id
     * @param mldDtlId 模具id
     * @return
     */
    @Override
    public Result exceOption(Integer opId, Integer optionId, Integer deviceId, Integer mldDtlId) {

        switch (optionId) {
            case 1:     // 装模/料 操作
                optionExceService.mouldFilling(opId, optionId, deviceId, mldDtlId);
                break;
            case 2:
                optionExceService.completeMouldFilling(opId, optionId, deviceId, mldDtlId);
                break;
            case 3:
                optionExceService.demoulding(opId, optionId, deviceId, mldDtlId);
                break;
            case 4:
                optionExceService.completeDemoulding(opId, optionId, deviceId, mldDtlId);
                break;
            case 5:
                optionExceService.mouldRepair(opId, optionId, deviceId, mldDtlId);
                break;
            case 6:
                optionExceService.demoulding2(opId, optionId, deviceId, mldDtlId);
                break;
            case 7:
                optionExceService.mouldRepairComplete(opId, optionId, deviceId, mldDtlId);
                break;
            case 8:
                optionExceService.mouldRepairComplete2(opId, optionId, deviceId, mldDtlId);
                break;
            case 9:
                optionExceService.startProduce(opId, optionId, deviceId, mldDtlId);
                break;
            case 10:
                optionExceService.continueProduce(opId, optionId, deviceId, mldDtlId);
                break;
            case 11:
                optionExceService.completeProduce(opId, optionId, deviceId, mldDtlId);
                break;
            case 12:
                optionExceService.suspendProduce(opId, optionId, deviceId, mldDtlId);
                break;
            case 13:
                optionExceService.deviceFault(opId, optionId, deviceId, mldDtlId);
                break;
            case 14:
                optionExceService.mouldFault(opId, optionId, deviceId, mldDtlId);
                break;
            case 15:
                optionExceService.revokeDeviceReportRepair(opId, optionId, deviceId, mldDtlId);
                break;
            case 16:
                optionExceService.revokeDeviceReportRepair2(opId, optionId, deviceId, mldDtlId);
                break;
            case 17:
                optionExceService.repairDevice(opId, optionId, deviceId, mldDtlId);
                break;
            case 18:
                optionExceService.completeRepairDevice(opId, optionId, deviceId, mldDtlId);
                break;
            case 19:
                optionExceService.checkProduce(opId, optionId, deviceId, mldDtlId);
                break;
            case 20:
                optionExceService.continueProduce2(opId, optionId, deviceId, mldDtlId);
                break;
            case 21:
                optionExceService.stopProduce(opId, optionId, deviceId, mldDtlId);
                break;
            case 22:
                optionExceService.nextTaskForPunch(opId, optionId, deviceId, mldDtlId);
                break;
            case 23:
                optionExceService.nextTaskForPBCB(opId, optionId, deviceId, mldDtlId);
                break;
            default:
                log.warn("执行动作id错误 optionId -> {}", optionId);
                break;
        }

        return null;
    }

    /**
     * 根据role id获取返回给前端的type类型
     *
     * @param roleId role id
     * @return type String
     */
    private String getType(Integer roleId) {
        switch (roleId) {
            case 8:
            case 9:
                return MLD;
            case 10:
                return PUNCH;
            case 7:
                return OVERHAUL;
            case 3:
                return PDCN;
            default:
                return UNKNOWN;
        }
    }
}
