package com.hfmes.sunshine.dto;

import com.hfmes.sunshine.domain.SCOption;
import lombok.Data;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 16:13
 */
@Data
public class OptionDTO {
    String name;
    Integer code;

    public OptionDTO(SCOption option) {
        this.name = option.getOptName();
        this.code = option.getScOptionId();
    }
}
