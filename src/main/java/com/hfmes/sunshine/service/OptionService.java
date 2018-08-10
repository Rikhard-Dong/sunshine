package com.hfmes.sunshine.service;

import com.hfmes.sunshine.domain.SCCondition;
import com.hfmes.sunshine.domain.SCMethod;
import com.hfmes.sunshine.dto.ConditionDto;
import com.hfmes.sunshine.dto.OptionDTO;
import com.hfmes.sunshine.dto.OptionsDTO;

import java.util.List;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 16:08
 */
public interface OptionService {

    /**
     * 获取当刷卡用户可操作的菜单按钮
     *
     * @param cardNo   卡号
     * @param deviceId 设备id
     * @return list of optionDTO
     */
    List<OptionDTO> obtainOptions(String cardNo, Integer deviceId);

    /**
     * 获取该操作的所需要满足的所有条件
     *
     * @param opId opID
     * @return list of condition
     */
    List<ConditionDto> obtainConditions(Integer opId);

    /**
     * 获取该操作的动作
     *
     * @param opId opId
     * @return list of method
     */
    List<SCMethod> obtainMethods(Integer opId);
}
