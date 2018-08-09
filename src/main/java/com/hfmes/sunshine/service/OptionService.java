package com.hfmes.sunshine.service;

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
    List<OptionsDTO> obtainOptions(String cardNo, Integer deviceId);
}
