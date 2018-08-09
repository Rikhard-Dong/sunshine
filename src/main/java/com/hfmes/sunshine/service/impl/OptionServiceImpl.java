package com.hfmes.sunshine.service.impl;

import com.hfmes.sunshine.dao.DevcDao;
import com.hfmes.sunshine.dao.RoleDao;
import com.hfmes.sunshine.dao.SCOptionDao;
import com.hfmes.sunshine.dao.TaskDao;
import com.hfmes.sunshine.domain.Devc;
import com.hfmes.sunshine.domain.SCOption;
import com.hfmes.sunshine.domain.Task;
import com.hfmes.sunshine.dto.OptionDTO;
import com.hfmes.sunshine.dto.OptionsDTO;
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

    private final Map<Integer, Devc> devcs;

    @Autowired
    public OptionServiceImpl(SCOptionDao optionDao,
                             RoleDao roleDao,
                             TaskDao taskDao,
                             @Qualifier("devcs") Map<Integer, Devc> devcs) {
        this.optionDao = optionDao;
        this.roleDao = roleDao;
        this.taskDao = taskDao;
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
    public List<OptionsDTO> obtainOptions(String cardNo, Integer deviceId) {


        Devc devc = devcs.get(deviceId);

        String deviceStatus = devc.getStatus();
        String mouldStatus = devc.getMldStatus();
        Task task = taskDao.findByTaskId(devc.getTaskId());
        String taskStatus = task.getStatus();

        // 获取操作的交集
        Set<SCOption> options = optionDao.findByCardNo(cardNo);
        options.retainAll(optionDao.findBySDStatus(deviceStatus));
        options.retainAll(optionDao.findBySMStatus(mouldStatus));
        options.retainAll(optionDao.findBySTStatus(taskStatus));
        log.debug("options --> {}", options);

        Map<Integer, OptionsDTO> result = new HashMap<>();

        for (SCOption option : options) {
            OptionsDTO optionsDTO = result.get(option.getRoleId());
            if (optionsDTO == null) {
                String roleTitle = roleDao.findTitleById(option.getRoleId());
                optionsDTO = new OptionsDTO(roleTitle);
                result.put(option.getRoleId(), optionsDTO);
            }
            OptionDTO optionDTO = new OptionDTO(option);
            optionsDTO.addData(optionDTO);
        }

        return new ArrayList<>(result.values());
    }
}
