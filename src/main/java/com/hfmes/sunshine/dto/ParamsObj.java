package com.hfmes.sunshine.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/10 9:13
 * <p>
 * 获取前端传递的参数对象
 */
@Data
public class ParamsObj implements Serializable {

    @JsonProperty("CardNo")
    private String cardNo;

    @JsonProperty("DeviceId")
    private Integer deviceId;

    @JsonProperty("PersonId")
    private Integer personId;

    @JsonProperty("DevcStatus")
    private String devcStatus;
}
