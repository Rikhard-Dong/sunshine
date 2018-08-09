package com.hfmes.sunshine.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 16:13
 */
@Data
public class OptionsDTO {
    String type;                                    // 操作角色
    Set<OptionDTO> options = new HashSet<>();     // 该角色可进行的操作

    public OptionsDTO() {
    }

    public OptionsDTO(String type) {
        this.type = type;
    }

    public void addData(OptionDTO optionDTO) {
        this.options.add(optionDTO);
    }
}
