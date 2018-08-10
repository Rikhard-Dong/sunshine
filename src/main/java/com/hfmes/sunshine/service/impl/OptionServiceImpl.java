package com.hfmes.sunshine.service.impl;

import com.hfmes.sunshine.dao.*;
import com.hfmes.sunshine.domain.*;
import com.hfmes.sunshine.dto.ConditionDto;
import com.hfmes.sunshine.dto.OptionDTO;
import com.hfmes.sunshine.service.OptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

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

    private static final String MLD = "MLD";            // 模具工
    private static final String PUNCH = "PUNCH";        // 冲床工
    private static final String OVERHAUL = "OVERHAUL";  // 检修工
    private static final String PDCN = "PDCB";          // 生产管理员
    private static final String UNKNOWN = "UNKNOWN";    // 未知类型

    @Autowired
    public OptionServiceImpl(SCOptionDao optionDao,
                             RoleDao roleDao,
                             TaskDao taskDao,
                             SCConditionDao conditionDao,
                             SCMethodDao methodDao,
                             SCOptCondDao scOptCondDao,
                             @Qualifier("devcs") Map<Integer, Devc> devcs) {
        this.optionDao = optionDao;
        this.roleDao = roleDao;
        this.taskDao = taskDao;
        this.conditionDao = conditionDao;
        this.methodDao = methodDao;
        this.scOptCondDao = scOptCondDao;
        this.devcs = devcs;
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
        String mouldStatus = devc.getMldStatus();
        Task task = taskDao.findByTaskId(devc.getTaskId());
        String taskStatus = task.getStatus();

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
     * @return
     */
    @Override
    public List<SCMethod> obtainMethods(Integer opId) {
        return methodDao.findByOpId(opId);
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
